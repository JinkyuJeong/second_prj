package admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminManageService;
import dto.Cs;
import dto.ProductOptView;
import dto.Stock;
import exception.ShopException;

@Controller
@RequestMapping("admin/cs")
public class AdminCsController {

	@Autowired
	private AdminManageService service;
	
	@RequestMapping("csList")
	public ModelAndView adminCsList(Integer pageNum, String sd, String ed, String query, String cs_state, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
	    if (sd == null || sd.equals("")) {
	    	sd = "";
	    }
	    if (ed == null || ed.equals("")) {
	    	ed="";
	    }
	    if (query == null || query.equals("")) {
	    	query="";
	    }
	    if (cs_state == null || cs_state.equals("")) {
	    	cs_state="";
	    }
		
		int csCnt = service.csCnt(sd, ed, query, cs_state);
		
		int limit = 10;
		int maxPage = (int)((double)csCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;
		
		List<Cs> csList =service.getCsList(pageNum, sd, ed, query, cs_state);
		
		mv.addObject("csList", csList);
		mv.addObject("csCnt", csCnt);
		mv.addObject("pageNum", pageNum);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("maxPage", maxPage);
		mv.addObject("sd", sd);
		mv.addObject("ed", ed);
		mv.addObject("cs_state", cs_state);
		return mv;
	}
	
	@GetMapping({"csRe", "csDetail"})
	public ModelAndView adminCsRe(Integer cs_number, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Cs cs = service.getCs(cs_number);
		if(cs == null) {
			throw new ShopException("해당 문의는 존재하지 않습니다.", "csList");
		}
		mv.addObject("cs",cs);
		return mv;
	}
	
	@PostMapping("csRe")
	public ModelAndView adminCsRe(Cs cs, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if(service.csReply(cs)) {
			mv.setViewName("redirect:csDetail?cs_number="+cs.getCs_number());
			return mv;
		}else {
			throw new ShopException("문의 답변 등록 실패", "csRe?cs_number="+cs.getCs_number());
		}
	}
}
