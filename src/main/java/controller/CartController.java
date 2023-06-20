package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public ModelAndView checkout(@RequestParam(value = "opt_numberChecked", required = false) String[] opt_numberChecked, String product_number, Integer[] opt_number, String[] quantity, HttpSession session) {
		ModelAndView mav = new ModelAndView();	
		if(product_number == null) { //장바구니에서 구매로
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
				mav.addObject("from", "cart");
			}
		} else { //바로 구매
			if(opt_number == null) {
				throw new ShopException("주문할 상품이 없습니다.", "/second_prj/product/productDetail?product_number="+product_number);
			} else {
				Mem mem = (Mem)session.getAttribute("loginMem");
			    String mem_id = mem.getMem_id();
				int total = 0;
				int discounted = 0;
				Map<Integer, String> map = new HashMap<>();
				for(int i=0; i<opt_number.length; i++) {
					map.put(opt_number[i], quantity[i]);
				}
				Map<ProductOptView, String> povList = new HashMap<>();
				for(Integer i : map.keySet()) {
					ProductOptView pov = service.getProductOptView(i);					
					total += pov.getProduct_price()* Integer.parseInt(map.get(i));
					discounted += pov.getProduct_price()* Integer.parseInt(map.get(i)) * ((double)pov.getProduct_discountRate()/100);
					povList.put(pov, map.get(i));
				}
				List<Delivery> deliveryList = service.getDeliveryList(mem_id);			   
				mav.addObject("deliveryList", deliveryList);
				mav.addObject("total",total);
				mav.addObject("discounted",discounted);
				mav.addObject("discountedTotal", total-discounted);	
				mav.addObject("mem",mem);
				mav.addObject("from","detail");
				mav.addObject("povList", povList);
			}
		}					
		return mav;
	}
	
	@RequestMapping("payment")
	@ResponseBody
	public Map<String, Object> payment(String final_amount, String[] product_name, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Mem mem = (Mem)session.getAttribute("loginMem");
		LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String formattedDate = today.format(formatter);
        int num= Integer.parseInt(service.getMaxOrderId().substring(6,10));
        String formattedNumber = String.format("%04d", num+1);
		String order_id = formattedDate + formattedNumber;		
		System.out.println(order_id);
		map.put("merchant_uid", order_id);
		map.put("name", product_name[0] + " 외 " + (product_name.length-1) + "개");
		map.put("amount", final_amount);
//		String email = service.emailDecrypt(user);
		map.put("buyer_email", mem.getMem_id());
		map.put("buyer_name", mem.getMem_name());
		map.put("buyer_tel", mem.getMem_phoneno());			
		return map; //클라이언트는 json 객체로 전달
	}
}
