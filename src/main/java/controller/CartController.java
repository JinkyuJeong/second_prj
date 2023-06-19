package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Cart;
import dto.Delivery;
import dto.Mem;
import dto.ProductOptView;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("cartAdd")
	public ModelAndView cart(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem loginMem = (Mem) session.getAttribute("loginMem");
		String mem_id = loginMem.getMem_id(); 
		List<Cart> cartList = service.getCartList(mem_id);
		Map<Cart, ProductOptView> map = new HashMap<>();
		int total = 0;
		int discounted = 0;
		for(Cart c : cartList) {			
			ProductOptView pov = service.getProductOptView(c.getOpt_number());
			total += pov.getProduct_price()* Integer.parseInt(c.getOpt_count());
			discounted += pov.getProduct_price()* Integer.parseInt(c.getOpt_count()) * ((double)pov.getProduct_discountRate()/100);
			map.put(c, pov);
		}
		mav.addObject("total",total);
		mav.addObject("discounted",discounted);
		mav.addObject("discountedTotal", total-discounted);
		mav.addObject("map", map);
		mav.addObject("cartList",cartList);
		return mav;
	}
	
	@RequestMapping("checkout")
	public ModelAndView checkout(@RequestParam(value = "opt_numberChecked", required = false) String[] opt_numberChecked, HttpSession session) {
		ModelAndView mav = new ModelAndView();		
		if(opt_numberChecked == null) {
			throw new ShopException("주문할 상품이 없습니다.", "cartAdd");
		} else {
			Mem mem = (Mem)session.getAttribute("loginMem");
		    String mem_id = mem.getMem_id();
		    List<Cart> cartList = new ArrayList<>();
		    Map<Cart, ProductOptView> map = new HashMap<>();
			for(String s : opt_numberChecked) {
				cartList.add(service.getCart(mem_id, Integer.parseInt(s)));
			}
			int total = 0;
			int discounted = 0;
			for(Cart c : cartList) {            
		        ProductOptView pov = service.getProductOptView(c.getOpt_number());
		        total += pov.getProduct_price()* Integer.parseInt(c.getOpt_count());
				discounted += pov.getProduct_price()* Integer.parseInt(c.getOpt_count()) * ((double)pov.getProduct_discountRate()/100);
		        map.put(c, pov);
		    }
			List<Delivery> deliveryList = service.getDeliveryList(mem_id);
			mav.addObject("deliveryList", deliveryList);
			mav.addObject("total",total);
			mav.addObject("discounted",discounted);
			mav.addObject("discountedTotal", total-discounted);
			mav.addObject("map",map);	
			mav.addObject("mem",mem);
		}			
		return mav;
	}
}
