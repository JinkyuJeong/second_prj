package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Cart;
import dto.Opt;
import dto.Product;
import dto.ProductOptView;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private ShopService service;
	
	@GetMapping("cartAdd")
	public ModelAndView cart() {
		ModelAndView mav = new ModelAndView();
		String mem_id = "test"; 
		List<Cart> cartList = service.getCartList(mem_id);
		Map<Cart, ProductOptView> map = new HashMap<>();
		List<Product> productList = new ArrayList<>();
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
		return mav;
	}
	
	@PostMapping("cartAdd")
	public ModelAndView cartAdd(String product_number, @RequestParam("opt_number")String[] opt_number, @RequestParam("quantity")String[] quantity) {
		ModelAndView mav = new ModelAndView();
		String mem_id = "test"; //member 등록하고 login구현 후 변경
		for(int i=0; i<opt_number.length; i++) {
			String optionNumber = opt_number[i];
			String optionCount = quantity[i];
			if(!service.addCart(mem_id, optionNumber, optionCount)) {
				throw new ShopException("장바구니 등록 시 오류 발생", "../product/productList");
			} 			
		}
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
		mav.addObject("map", map);
		mav.addObject("total",total);
		mav.addObject("discounted",discounted);
		mav.addObject("discountedTotal", total-discounted);
		return mav;
	}
}
