package admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminManageService;
import dto.Cs;

@Controller
@RequestMapping("admin/cs")
public class AdminCsController {

	@Autowired
	private AdminManageService service;
	
	@RequestMapping("csList")
	public ModelAndView adminCsList(Integer pageNum, String sd, String ed, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
	    if (sd == null || sd.equals("")) {
	    	sd = "";
	    }
	    if (ed != null && ed.equals("")) {
	    	ed="";
	    }
		
		int csCnt = service.csCnt(sd, ed);
		
		int limit = 10;
		int maxPage = (int)((double)csCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;
		
		List<Cs> csList =service.getCsList(pageNum, sd, ed);
		
		mv.addObject("csList", csList);
		mv.addObject("csCnt", csCnt);
		mv.addObject("pageNum", pageNum);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("maxPage", maxPage);
		mv.addObject("sd", sd);
		mv.addObject("ed", ed);
		return mv;
	}
}
