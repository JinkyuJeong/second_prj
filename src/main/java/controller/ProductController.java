package controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dto.Opt;
import dto.Product;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ShopService service;
	
	@RequestMapping("productList")
	public ModelAndView productList(@RequestParam Map<String, String> param, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Integer pageNum = null;
		if(param.get("pageNum") != null) pageNum = Integer.parseInt(param.get("pageNum"));
		String product_type = param.get("product_type");
		String searchContent = param.get("searchContent");
		String product_type_name = "";
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if(product_type == null || product_type.equals("")) {
			product_type = null;
			product_type_name = "전체 상품";
		}
//		product_type_name = service.getProduct_type_name(product_type); //카테고리 테이블 만들면 추가
		if(searchContent == null || searchContent.equals("")) searchContent="";
		session.setAttribute("product_type", product_type);
		int limit=9;
		int productCount = service.productCount(product_type, searchContent); 
		
		int maxpage = (int)((double)productCount / limit + 0.95);
		int startpage = ((int)(pageNum / 9.0 + 0.9) - 1) * 9 + 1;
		int endpage = startpage + 8;
		if (endpage > maxpage) endpage = maxpage;
		
		List<Product> productList = service.productList(pageNum, limit, product_type, searchContent);
		
		//정렬
		String sort = param.get("sort");
		if(param.get("sort") != null) {
			switch(sort) {
			case "2" : Collections.sort(productList, (u1,u2)-> u1.getProduct_price()*(100-u1.getProduct_discountRate())/100 - (u2.getProduct_price()*(100-u2.getProduct_discountRate())/100)); break;
			case "3" : Collections.sort(productList, (u1,u2)-> u2.getProduct_price()*(100-u2.getProduct_discountRate())/100 - (u1.getProduct_price()*(100-u1.getProduct_discountRate())/100)); break;
			}
		}
		mav.addObject("productList",productList);
		mav.addObject("pageNum",pageNum);
		mav.addObject("maxpage",maxpage);
		mav.addObject("startpage",startpage);
		mav.addObject("endpage",endpage);
		mav.addObject("productCount",productCount);
		mav.addObject("searchContent",searchContent);
		mav.addObject("product_type_name",product_type_name);
		return mav;
	}
	
	@GetMapping("productDetail")
	public ModelAndView productDetail(Integer product_number) {
		ModelAndView mav = new ModelAndView();
		Product product = service.getProduct(product_number);
		if(product == null) {
			throw new ShopException("해당 상품은 존재하지 않습니다.", "productList");
		}
		List<Opt> optList = service.getOption(product.getProduct_number());
		String[] product_pictures = product.getProduct_pictures().split(",");
		List<String> product_pircturesList = Arrays.asList(product_pictures);
		mav.addObject("product",product);
		mav.addObject("product_pircturesList", product_pircturesList);
		mav.addObject("optList", optList);
		return mav;
	}
}
