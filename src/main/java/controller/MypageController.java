package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Cs;
import dto.Delivery;
import dto.Mem;
import dto.OrderView;
import dto.Point;
import dto.ProductOptView;
import dto.Refund;
import dto.Review;
import exception.CloseException;
import exception.ShopException;
import service.ShopService;
import util.CipherUtil;

@Controller
@RequestMapping("mypage")
public class MypageController {
	@Autowired
	private ShopService service;
	@Autowired
	private CipherUtil cipher;
	
	private String passwordHash(String password) throws Exception {
		return cipher.makehash(password, "SHA-512");
	}
	
	@GetMapping("*")
	public ModelAndView all() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@GetMapping("myInfo")
	public ModelAndView idCheckmyInfo(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem mem = service.getMemEmail(mem_id);
		mav.addObject("mem", mem);
		return mav;
	}
	
	@GetMapping("orderList")
	public ModelAndView idCheckorderList(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();		
		Mem mem = (Mem) session.getAttribute("loginMem");
		List<OrderView> ov = service.getOv(mem.getMem_id());
		Map<String, List<OrderView>> map = new LinkedHashMap<>();
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
		Map<String, List<Refund>> map = new LinkedHashMap<>();
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
	public ModelAndView idCheckRefundReg(Integer order_itemId, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<OrderView> myOvList = service.getOv(mem_id);
		Mem sessionMem = (Mem) session.getAttribute("loginMem");
		OrderView ov = service.getOvItemId(order_itemId);
		System.out.println(ov);
		if(ov == null) {
			throw new ShopException("존재하지 않는 주문입니다.", "orderList?mem_id=" + mem_id);
		}
		if(!ov.getMem_id().equals(mem_id)) {
			throw new ShopException("주문자가 일치하지 않습니다.", "orderList?mem_id=" + mem_id);
		} 
		if(!ov.getOrder_state().equals("배송완료")) {
			throw new ShopException("배송이 완료되지 않은 상품은 환불할 수 없습니다.", "orderList?mem_id="+mem_id);
		}
		//주문일로부터 2주내의 주문들만 출력
	    Calendar twoWeeksAgo = Calendar.getInstance();
	    twoWeeksAgo.add(Calendar.DATE, -14);
		if(ov.getOrder_date().before(twoWeeksAgo.getTime())) {
			throw new ShopException("구매한지 2주가 지난 상품은 환불할 수 없습니다.", "orderList?mem_id="+mem_id);
		}
		mav.addObject("ov", ov);	   
		return mav;
	}
	
	@PostMapping("refundReq")
	public ModelAndView idCheckRefundReqPost (String order_id, String order_itemId, Integer opt_number, String refund_reason, Integer refund_optCount, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem sessionMem = (Mem) session.getAttribute("loginMem");
		//refund table insert
		/*
		 * 환불 -> 이미 환불 내역이 있음 -> 주문한 수량보다 많이 환불x
			  					  -> 주문한 수량보다 적다면 -> 환불 insert
     		   -> 환불 내역 없음 -> 환불 insert
		 */
		Map<Integer, Integer> optMap = new HashMap<>();	
		List<Refund> rf = service.getRefund(order_id, opt_number);
		OrderView ov = service.getOvIdNum(order_id, opt_number);
		
		ProductOptView pov = service.getProductOptView(opt_number);		
		int price = (pov.getProduct_price() * (100 - pov.getProduct_discountRate())/100) * refund_optCount;
		if(rf==null || rf.size()==0) {					
			if(!service.refundInsert(order_id, opt_number, refund_optCount, mem_id, refund_reason, price)) {
				throw new ShopException("죄송합니다. 환불 과정에서 오류가 발생했습니다.", "orderList?mem_id=" + mem_id);
			}
		} else {
			int ogOptCount = 0;
			for(Refund r : rf) {
				ogOptCount += r.getRefund_optCount();
			}
			ogOptCount += refund_optCount;
			if(ogOptCount > Integer.parseInt(ov.getOpt_count())) {
				throw new ShopException("환불 내역을 확인해주세요. \\n주문한 수량보다 많이 환불할 수 없습니다.", "refundList?mem_id=" + sessionMem.getMem_id());
			} else {
				if(!service.refundInsert(order_id, opt_number, refund_optCount, mem_id, refund_reason, price)) {
					throw new ShopException("죄송합니다. 환불 과정에서 오류가 발생했습니다.", "orderList?mem_id=" + sessionMem.getMem_id());
				}
			}
		}			
		mav.setViewName("redirect:refundList?mem_id=" + sessionMem.getMem_id());
		return mav;
	}
	
	@GetMapping("refundList")
	public ModelAndView idCheckrefundList(String refund_type, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Refund> refundList = new ArrayList<>();
		if(refund_type == null) {
			refundList = service.getRefundListAll(mem_id, "주문취소"); //refund_type이 주문취소인거 빼고 다
		} else if(refund_type.equals("환불대기")) {
			refundList = service.getRefundCancelList(mem_id, "환불대기");
		} else if(refund_type.equals("환불완료")) {
			refundList = service.getRefundCancelList(mem_id, "환불완료");
		} else if(refund_type.equals("환불반려")) {
			refundList = service.getRefundCancelList(mem_id, "환불반려");
		}
		Map<Refund, ProductOptView> map = new LinkedHashMap<>();
		for (Refund refund : refundList) {
		    int opt_number = refund.getRefund_optId();
		    ProductOptView pov = service.getProductOptView(opt_number);
		    map.put(refund, pov);
		}
		mav.addObject("map", map);
		mav.addObject("refundList",refundList);
		return mav;
	}
	
	@GetMapping("reviewReg")
	public ModelAndView idCheckReviewReg(Integer order_itemId, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Review review = service.getReviewIsWritten(order_itemId, mem_id);
		if(review != null) {
			throw new ShopException("이미 작성한 리뷰가 있습니다.", "reviewList?mem_id="+mem_id);
		}
		OrderView ov = service.getOvItemId(order_itemId);
		mav.addObject("ov", ov);
		return mav;
	}
	
	@PostMapping("reviewReg")
	public ModelAndView idCheckReviewRegPost(Integer order_itemId, Integer review_value, String review_content,String mem_id, HttpSession session) {
		if(service.addReview(order_itemId, review_value, review_content, mem_id)) {
			throw new ShopException("감사합니다. 리뷰가 등록되었습니다.", "reviewList?mem_id="+mem_id);
		} else {
			throw new ShopException("죄송합니다. 리뷰 등록 중 오류가 발생했습니다.", "orderList?mem_id="+mem_id);
		}
	}
	
	@GetMapping("reviewList")
	public ModelAndView idCheckReviewList(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Review> reviewList = service.getMyReview(mem_id);
		Map<Review, OrderView> map = new LinkedHashMap<>();
		for(Review r : reviewList) {
			OrderView ov = service.getOvItemId(r.getOrder_itemId());
			map.put(r, ov);
		}
		mav.addObject("map",map);
		return mav;
	}
	
	@GetMapping("reviewUpdate")
	public ModelAndView idCheckReviewUpdate(Integer review_number, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Review review = service.getReviewNum(review_number);
		OrderView ov = service.getOvItemId(review.getOrder_itemId());
		mav.addObject("ov", ov);
		return mav;
	}
	
	@PostMapping("reviewUpdate")
	public ModelAndView idCheckReviewUpdatePost(Integer review_number, Integer review_value, String review_content, String mem_id, HttpSession session) {
		if(service.updateReview(review_number, review_value, review_content)) {
			throw new ShopException("리뷰가 성공적으로 수정되었습니다.", "reviewList?mem_id="+mem_id);
		} else {
			throw new ShopException("죄송합니다. 리뷰 수정 중 오류가 발생했습니다.", "reviewList?mem_id="+mem_id);
		}
	}
	
	@RequestMapping("reviewDelete")
	public String idCheckReviewDelete(Integer review_number, String mem_id, HttpSession session) {
		Review review = service.getReviewNum(review_number);
		if(review.getReview_state().equals("지급완료")) {
			throw new ShopException("이미 포인트가 지급된 건에 대해서는 리뷰 삭제가 불가합니다.", "reviewList?mem_id="+mem_id);
		}
		if(!service.deleteReview(review_number)) {
			throw new ShopException("죄송합니다. 리뷰 삭제 중 오류가 발생했습니다.", "reviewList?mem_id="+mem_id);
		} else {
			return "redirect:reviewList?mem_id="+mem_id;
		}
	}
	
	@GetMapping("pointList")
	public ModelAndView idCheckPointList(String point_type, String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<Point> pointList = new ArrayList<>();
		if(point_type == null || point_type.equals("")) {
			pointList = service.getMyPoint(mem_id);
		} else if (point_type.equals("else")) {
			pointList = service.getMyPointReceived(mem_id, "포인트 사용");
		} else if (point_type.equals("used")) {
			pointList = service.getMyPointUsed(mem_id, "포인트 사용");
		}
		mav.addObject("pointList",pointList);
		return mav;
	}
	
	@GetMapping("myInfoUpdate")
	public ModelAndView idCheckmyInfoUpdate(String mem_id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem mem = service.getMemEmail(mem_id);
		mav.addObject("mem", mem);
		return mav;
	}
	
	@PostMapping("myInfoUpdate")
	public ModelAndView idCheckmyInfoUpdatePost(Mem mem, String mem_id, HttpSession session) {
		if(!service.updateMem(mem)) {
			throw new ShopException("죄송합니다. 회원 정보 수정 과정에서 오류가 발생했습니다.","myInfo?mem_id="+mem_id);
		} else {
			Mem newMem = service.getMemEmail(mem_id);
			session.setAttribute("loginMem", newMem);
			throw new ShopException("회원 정보 수정이 완료되었습니다.", "myInfo?mem_id="+mem_id);
		}		
	}
	
	@PostMapping("memDelete")
	public String idCheckmemDelete(String mem_pw, String mem_id, HttpSession session) {
		Mem dbMem = service.getMemEmail(mem_id);
		String hashPass = "";
		try {
			hashPass = passwordHash(mem_pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!dbMem.getMem_pw().equals(hashPass)) {
			throw new ShopException("비밀번호를 확인하세요.", "memDelete?mem_id="+mem_id);
		} else {
			if(!service.deleteMem(mem_id)) {
				throw new ShopException("죄송합니다. 회원 탈퇴 과정에서 오류가 발생했습니다.", "/second_prj/");
			} else {
				throw new ShopException("회원 탈퇴가 완료되었습니다.", "/second_prj/");
			}
		}
	}
	
}
