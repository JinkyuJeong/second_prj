package admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminShopService;
import dto.Opt;
import dto.ProductOptView;
import dto.Stock;
import exception.ShopException;

@Controller
@RequestMapping("admin/stock")
public class AdminStockController {

	@Autowired
	private AdminShopService service;
	
	@GetMapping("stockReg")
	public ModelAndView adminStockReg(Integer opt_number,HttpSession session) {
		ModelAndView mv = new ModelAndView();
		ProductOptView prodOpt = service.getProdOpt(opt_number);
		if(prodOpt == null) {
			throw new ShopException("해당 제품 옵션은 존재하지 않습니다.", "optList");
		}
		mv.addObject("prodOpt",prodOpt);
		return mv;
	}
	
	@PostMapping("stockReg")
	public ModelAndView adminStockReg(Stock stock, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(service.regProdStock(stock)) {
			mv.setViewName("redirect:stockList");
			return mv;
		}else {
			throw new ShopException("재고 등록 실패", "redirect:stockReg?opt_number="+stock.getOpt_number());
		}
	}
	
	@RequestMapping("stockList")
	public ModelAndView adminOptList(Integer pageNum, String query, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if(query == null || query.equals("")) {
			query = "";
		}
		
		int stockCnt = service.getStockCnt(query);
		
		int limit = 10;
		int maxPage = (int)((double)stockCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;
		
		List<Stock> stockList =service.getStockList(pageNum, query);
		
		mv.addObject("stockList", stockList);
		mv.addObject("stockCnt", stockCnt);
		mv.addObject("pageNum", pageNum);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("maxPage", maxPage);
		return mv;
	}
	
	@GetMapping("stockChg")
	public ModelAndView adminStockChg(Integer stock_number, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Stock stock = service.getStock(stock_number);
		
		if(stock == null) {
			throw new ShopException("해당 재고 내역은 존재하지 않습니다.", "stockList");
		}
		
		mv.addObject("stock",stock);
		return mv;
	}
	
	@PostMapping("stockChg")
	public ModelAndView adminStockChg(Stock stock, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(service.updateStock(stock)) {
			mv.setViewName("redirect:stockList");
			return mv;
		}else {
			throw new ShopException("옵션 변경 실패", "redirect:stockList");
		}
	}
}
