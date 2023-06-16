package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Opt;
import dto.Product;
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
}
