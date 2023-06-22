package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dto.Cs;
import dto.Mem;
import exception.ShopException;
import service.ShopService;

@Controller
@RequestMapping("/*")
public class IndexController {
	
	@Autowired
	private ShopService service;
	
    @RequestMapping("/index")
    public String root() {
        return "main";
    }
    
    @RequestMapping("/qna")
    public String qna() {
        return "qna";
    }
    
    @GetMapping("/cs")
    public ModelAndView cs(HttpSession session) {
    	ModelAndView mv = new ModelAndView();
    	Mem loginMem = (Mem) session.getAttribute("loginMem");
    	mv.addObject("loginMem", loginMem);
        return mv;
    }
    
    @PostMapping("/csQ")
    public ModelAndView csQ(Cs cs,HttpSession session){
    	ModelAndView mv = new ModelAndView();
    	if(service.csReg(cs)) {
    		mv.setViewName("redirect:mypage/cs?mem_id="+cs.getMem_id());
			return mv;
		}else {
			throw new ShopException("문의 내용 전송 실패", "cs");
		}
    }
    
}
