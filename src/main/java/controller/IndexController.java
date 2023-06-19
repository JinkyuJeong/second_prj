package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class IndexController {
	
    @RequestMapping("/index")
    public String root() {
        return "main";
    }
    
    @RequestMapping("/qna")
    public String qna() {
        return "qna";
    }
}
