package admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminManageService;
import dto.Chall;

@Controller
@RequestMapping("admin/chall")
public class AdminChallController {

	@Autowired
	private AdminManageService service;
	
	@RequestMapping("challList")
	public ModelAndView adminChallList(Integer pageNum, String query, String sd, String ed, String chall_state, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if(query == null || query.equals("")) {
			query = "";
		}
		if (sd == null || sd.equals("")) {
			sd = "";
		}
		if (ed == null || ed.equals("")) {
			ed="";
		}
		if (chall_state == null || chall_state.equals("")) {
			chall_state="";
		}

		int challCnt = service.challCnt(query, sd, ed, chall_state);

		int limit = 10;
		int maxPage = (int)((double)challCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;

		List<Chall> challList =service.getChallList(pageNum, query, sd, ed, chall_state);
		
		List<Integer> userDateList = new ArrayList<>();
		for(Chall c : challList) {
			Integer cnt = service.dateCnt(c.getMem_id());
			userDateList.add(cnt);
		}

		mv.addObject("challCnt", challCnt);
		mv.addObject("userDateList", userDateList);
		mv.addObject("challList", challList);
		mv.addObject("pageNum", pageNum);
		mv.addObject("startPage", startPage);
		mv.addObject("endPage", endPage);
		mv.addObject("maxPage", maxPage);
		mv.addObject("sd", sd);
		mv.addObject("ed", ed);
		mv.addObject("chall_state", chall_state);
		return mv;
	}
}
