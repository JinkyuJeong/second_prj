package controller;

import java.util.ArrayList;
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
		List<Refund> refundList = new ArrayList<>();
		if(refund_type == null) {
			refundList = service.getRefundList(mem_id);
		} else if(refund_type.equals("취소")) {
			refundList = service.getRefundCancelList(mem_id, "취소");
		} else if(refund_type.equals("환불대기")) {
			refundList = service.getRefundCancelList(mem_id, "환불대기");
		} else if(refund_type.equals("환불완료")) {
			refundList = service.getRefundCancelList(mem_id, "환불완료");
		}		
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
		if(order_id == null || order_id.equals("")) {
			throw new ShopException("마이페이지 > 주문목록에서 환불신청을 해주세요. \\n환불신청은 주문완료된 건들만 가능합니다.", "orderList?mem_id=" + mem_id);
		} else {
			List<OrderView> orderItems = service.getOvList(mem_id, order_id);
			mav.addObject("orderItems", orderItems);
		}		
		return mav;
	}
}
