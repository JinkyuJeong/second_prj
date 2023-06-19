package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import dto.Cart;
import dto.Delivery;
import dto.Mem;
import dto.Opt;
import dto.Product;
import dto.ProductOptView;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("ajax")
public class AjaxController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("optionSelect")
	public ModelAndView optionSelect(@RequestParam(name = "opt_number") Integer opt_number) {
		ModelAndView mav = new ModelAndView();
		Opt opt = service.getOptionByNum(opt_number);
		Product product = service.getProduct(opt.getProduct_number());
		int product_price = product.getProduct_price();
		int product_discountRate = product.getProduct_discountRate();
		String discountedPrice = Integer.toString(product_price * (100-product_discountRate)/100);
		mav.addObject("opt_number",opt.getOpt_number());
		mav.addObject("opt_name", opt.getOpt_name());
		mav.addObject("discountedPrice",discountedPrice);
		return mav;
	}
	
	@RequestMapping("cartAdd")
	@ResponseBody
	public String cartAdd(@RequestParam(value="opt_number", required=false)String[] opt_number, @RequestParam(value="quantity", required=false)String[] quantity, HttpSession session) {
		Mem loginMem = (Mem) session.getAttribute("loginMem");
		String mem_id = loginMem.getMem_id(); 
		if(opt_number!=null || quantity != null) {
			for(int i=0; i<opt_number.length; i++) {
				String optionNumber = opt_number[i];
				String optionCount = quantity[i];
				if(!service.addCart(mem_id, optionNumber, optionCount)) { //addCart : 장바구니 insert, update
					throw new ShopException("장바구니 등록 시 오류 발생", "../product/productList");
				} 			
			}
		}
		return "장바구니 담기 성공";
	}
	
	@RequestMapping("passChk")
	public ModelAndView passChk(String pass, String pass2) {
		ModelAndView mav = new ModelAndView();
		boolean b = true;
		String emptyChk = null;
		String emptyChk2 = null;
		if(pass2==null || pass2.equals("")){
			emptyChk="emptyChk";
		 } else{
			 emptyChk="";
		 }
		if(pass.equals(pass2)) {
			b=true;
		} else {
			b=false;			 
		} 
		mav.addObject("b", b);
		mav.addObject("emptyChk", emptyChk);
		mav.addObject("emptyChk2", emptyChk2);
		mav.setViewName("ajax/passChk");
		return mav;
	}
	@RequestMapping("corrPassChk")
	public ModelAndView corrPassChk(String pass, String pass2) {
		ModelAndView mav = new ModelAndView();
		String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,16}$";
		boolean b = true;
		String emptyChk = null;
		if(pass==null || pass.equals("")){
			emptyChk="emptyChk";
		} else {
			 emptyChk="";
		}
		if(pass.matches(pattern)) {
			b=true;
		} else {
			b=false;
		}
		mav.addObject("b", b);
		mav.addObject("emptyChk", emptyChk);
		mav.setViewName("ajax/corrPassChk");
		return mav;
	}
	
	@RequestMapping("cartDelete")
	@ResponseBody
	public String cartDelete(Integer opt_number, HttpSession session) {
		Mem mem = (Mem)session.getAttribute("loginMem");
		String mem_id = mem.getMem_id();
		if(service.cartDelete(opt_number, mem_id)) {
			return "[ajax] 장바구니 삭제 성공";
		} else {
			return "[ajax] 장바구니 삭제 실패";
		}		
	}
	
	@RequestMapping("cartMinus")
	@ResponseBody
	public String cartMinus(Integer opt_number, HttpSession session) {
		Mem mem = (Mem)session.getAttribute("loginMem");
		String mem_id = mem.getMem_id();
		if(service.cartMinus(opt_number, mem_id)) {
			return "[ajax] 장바구니 수량 - 성공";
		} else {
			return "[ajax] 장바구니 수량 - 실패";
		}
		
	}
	
	@RequestMapping("cartPlus")
	@ResponseBody
	public String cartPlus(Integer opt_number, HttpSession session) {
		Mem mem = (Mem)session.getAttribute("loginMem");
		String mem_id = mem.getMem_id();
		if(service.cartPlus(opt_number, mem_id)) {
			return "[ajax] 장바구니 수량 + 성공";
		} else {
			return "[ajax] 장바구니 수량 + 실패";
		}
	}
	
	@RequestMapping("cartCalculate")
	@ResponseBody
	public Map<String, Object> cartCalculate(@RequestBody String[] optNumbers, HttpSession session) {
	    Map<String, Object> map = new HashMap<>();
	    Mem mem = (Mem)session.getAttribute("loginMem");
	    String mem_id = mem.getMem_id();
	    List<Cart> cartList = new ArrayList<>();
	    for(String i : optNumbers) {
	        cartList.add(service.getCart(mem_id, Integer.parseInt(i)));
	    }
	    int total = 0;
	    int discounted = 0;
	    for(Cart c : cartList) {            
	        ProductOptView pov = service.getProductOptView(c.getOpt_number());
	        total += pov.getProduct_price()* Integer.parseInt(c.getOpt_count());
	        discounted += pov.getProduct_price()* Integer.parseInt(c.getOpt_count()) * ((double)pov.getProduct_discountRate()/100);
	    }
	    map.put("total", total);
	    map.put("discounted", discounted);
	    return map;
	}
	
	@RequestMapping(value = "deliverySave", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String deliverySave(Delivery delivery, HttpSession session) {
		String msg = "";
		Mem mem = (Mem) session.getAttribute("loginMem");
		String mem_id = mem.getMem_id();
		System.out.println(delivery.getDelivery_postcode());
		if(service.addDelivery(delivery, mem_id)) {
			msg = "배송지가 저장되었습니다.";
		} else {
			msg = "죄송합니다. 배송지 저장에 실패했습니다.";
		}
		return msg;
	}
	
	@RequestMapping("deliverySelect")
	@ResponseBody
	public Delivery deliverySelect(@RequestParam("selectedOption") Integer selectedOption) {
	    Delivery delivery = service.getDelivery(selectedOption);
	    return delivery;
	}
}
