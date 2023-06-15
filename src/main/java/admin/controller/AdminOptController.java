package admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminShopService;
import dto.Opt;
import dto.Product;
import exception.ShopException;

@Controller
@RequestMapping("admin/opt")
public class AdminOptController {

	@Autowired
	private AdminShopService service;
	
	@GetMapping("optReg")
	public ModelAndView adminRegOpt(Integer product_number,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Product product = service.getProd(product_number);
		if(product == null) {
			throw new ShopException("해당 제품은 존재하지 않습니다.", "prodList");
		}
		mv.addObject("product",product);
		return mv;
	}
	
	@PostMapping("optReg")
	public ModelAndView adminRegOpt(Opt opt, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(service.regProductOpt(opt)) {
			mv.setViewName("redirect:prodList");
			return mv;
		}else {
			throw new ShopException("옵션등록 실패", "redirect:optReg?product_number="+opt.getProduct_number());
		}
	}
}
