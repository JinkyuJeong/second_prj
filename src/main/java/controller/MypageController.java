package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dto.Cs;
import dto.Delivery;
import dto.Mem;
import dto.OrderView;
import dto.ProductOptView;
import dto.Refund;
import exception.CloseException;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("mypage")
public class MypageController {
	@Autowired
	private ShopService service;
	
	@GetMapping("*")
	public ModelAndView all() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@GetMapping("myInfo")
	public ModelAndView idCheckmyInfo(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem mem = (Mem) session.getAttribute("loginMem");
		mav.addObject("mem", mem);
		return mav;
	}
	
	@GetMapping("orderList")
	public ModelAndView idCheckorderList(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();		
		Mem mem = (Mem) session.getAttribute("loginMem");
		List<OrderView> ov = service.getOv(mem.getMem_id());
		Map<String, List<OrderView>> map = new HashMap<>();
		List<Integer> priceList = new ArrayList<>();
		for(OrderView o : ov) {
			List<OrderView> ovList = service.getOvList(mem.getMem_id(), o.getOrder_id());
			map.put(o.getOrder_id(), ovList);
		}
		mav.addObject("ov", ov);
		mav.addObject("orderCnt", ov.size());
		mav.addObject("map",map);
		return mav;
	}
	
	@GetMapping("deliveryList")
	public ModelAndView idCheckdeliveryList(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Delivery> deliveryList = service.getDeliveryList(mem_id);
		mav.addObject("deliveryList", deliveryList);
		return mav;
	}
	
	@PostMapping("deliveryList")
	public ModelAndView loginCheckdeliveryListPost(Delivery delivery, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem sessionMem = (Mem) session.getAttribute("loginMem");
		if(service.newD(delivery, sessionMem.getMem_id())) {
			throw new CloseException("배송지가 추가되었습니다.", true);
		}
		List<Delivery> deliveryList = service.getDeliveryList(sessionMem.getMem_id());
		mav.addObject("deliveryList", deliveryList);
		return mav;
	}
	
	@GetMapping("cancelList")
	public ModelAndView idCheckcancelList(String refund_type, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Refund> refundList = service.getRefundCancelList(mem_id, "결제 및 주문 취소");
		Map<String, List<Refund>> map = new HashMap<>();
		for (Refund refund : refundList) {
		    String orderId = refund.getRefund_orderId();
		    List<Refund> reRefundList = service.getRefundListOrderId(orderId);
		    map.put(orderId, reRefundList);
		}
		mav.addObject("map", map);
		mav.addObject("refundList",refundList);
		return mav;
	}
	
	@GetMapping("cs")
	public ModelAndView idCheckCs(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Cs> csList = service.getCs(mem_id);
		mav.addObject("csList", csList);
		return mav;
	}
	
	@GetMapping("refundReq")
	public ModelAndView idCheckRefundReg(String order_id, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<OrderView> orderItems = service.getOvDelivered(mem_id, "배송완료");
		
		//주문일로부터 2주내의 주문들만 출력
	    List<OrderView> recentOrders = new ArrayList<>();
	    Calendar twoWeeksAgo = Calendar.getInstance();
	    twoWeeksAgo.add(Calendar.DATE, -14);
	    for (OrderView order : orderItems) {
	        if (order.getOrder_date().after(twoWeeksAgo.getTime())) {
	            recentOrders.add(order);
	        }
	    }
	    System.out.println(orderItems);
		mav.addObject("orderItems", recentOrders);	
		return mav;
	}
	
	@PostMapping("refundReq")
	public ModelAndView idCheckRefundReqPost (String[] order_id, Integer[] opt_numbers, Integer[] opt_count, String[] refund_reasons, String refund_memId, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem sessionMem = (Mem) session.getAttribute("loginMem");
		//refund table insert
		/*
		 * 환불 -> 이미 환불 내역이 있음 -> 주문한 수량보다 많이 환불x
			  					  -> 주문한 수량보다 적다면 -> 환불 insert
     		   -> 환불 내역 없음 -> 환불 insert
		 */
		Map<Integer, Integer> optMap = new HashMap<>();		
		for(int i=0; i< opt_numbers.length; i++) {
			List<Refund> rf = service.getRefund(order_id[i], opt_numbers[i]);
			int refund_optCount = 0;
			for(Refund r : rf) {
				refund_optCount += r.getRefund_optCount();
			}			
			refund_optCount += opt_count[i];
			ProductOptView pov = service.getProductOptView(opt_numbers[i]);
			OrderView ov = service.getOvIdNum(order_id[i], opt_numbers[i]);
			int price = (pov.getProduct_price() * (100 - pov.getProduct_discountRate())/100) * opt_count[i];
			String refund_reason = refund_reasons[i];
			if(rf==null || rf.size()==0) {					
				if(!service.refundInsert(order_id[i], opt_numbers[i], opt_count[i], refund_memId, refund_reason, price)) {
					throw new ShopException("죄송합니다. 환불 과정에서 오류가 발생했습니다.", "orderList?mem_id=" + sessionMem.getMem_id());
				}
			} else {
				if(refund_optCount > Integer.parseInt(ov.getOpt_count())) {
					throw new ShopException("환불 내역을 확인해주세요. \\n주문한 수량보다 많이 환불할 수 없습니다.", "refundList?mem_id=" + sessionMem.getMem_id());
				} else {
					if(!service.refundInsert(order_id[i], opt_numbers[i], opt_count[i], refund_memId, refund_reason, price)) {
						throw new ShopException("죄송합니다. 환불 과정에서 오류가 발생했습니다.", "orderList?mem_id=" + sessionMem.getMem_id());
					}
				}
			}			
			optMap.put(opt_numbers[i], opt_count[i]);
			List<Refund> refundList = service.getRefundListOrderId(order_id[i]);
			for(Refund r : refundList) {
				if(!r.getRefund_reason().equals("제품 결함")) {
					//옵션 수량 체크
					int count = r.getRefund_optCount();
					if(!service.updateQ(r.getRefund_optId(), r.getRefund_optCount())) {
						throw new ShopException("옵션수량 체크 과정에서 문제가 발생했습니다.", "orderList?mem_id=" + sessionMem.getMem_id());
					}				
				}
			}
		}		
		//refund_reason이 "제품 결함"이 아닌 경우
		//opt_quantity 수량 update
		mav.setViewName("redirect:refundList?mem_id=" + sessionMem.getMem_id());
		return mav;
	}
	
	@GetMapping("refundList")
	public ModelAndView idCheckrefundList(String refund_type, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Refund> refundList = new ArrayList<>();
		if(refund_type == null) {
			refundList = service.getRefundListAll(mem_id, "환불대기", "환불승인");
		} else if(refund_type.equals("환불대기")) {
			refundList = service.getRefundCancelList(mem_id, "환불대기");
		} else if(refund_type.equals("환불승인")) {
			refundList = service.getRefundCancelList(mem_id, "환불승인");
		}
		Map<Refund, ProductOptView> map = new HashMap<>();
		for (Refund refund : refundList) {
		    int opt_number = refund.getRefund_optId();
		    ProductOptView pov = service.getProductOptView(opt_number);
		    map.put(refund, pov);
		}
		mav.addObject("map", map);
		mav.addObject("refundList",refundList);
		return mav;
	}
}
