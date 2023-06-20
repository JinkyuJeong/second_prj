package admin.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminManageService;
import dto.Manager;
import exception.ShopException;

@Controller
@RequestMapping("admin")
public class AdminMainController {
	
	@Autowired
	private AdminManageService service;
	
	@RequestMapping("main")
    public String adminMain(HttpSession session) {
        return "admin/main";
    }
	
	@RequestMapping("qna")
	public String adminMqna(HttpSession session) {
		return "admin/qna";
	}
	
	@GetMapping("login")
	public String login() {
		return "admin/login";
	}
	
	@PostMapping("login")
	public ModelAndView login(String manager_id, String manager_pass, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Manager dbManager = service.managerLogin(manager_id, manager_pass);
		
		if(dbManager == null) {
			throw new ShopException("아이디 또는 비밀번호를 확인하세요.", "login");
		}else {
			session.setAttribute("loginManager", dbManager);
			mv.setViewName("redirect:main");
		}
		
		return mv;
	}
	
	@RequestMapping("logout")
	public String adminLogout(HttpSession session) {
		session.invalidate();
		return "redirect:login";
	}
}
