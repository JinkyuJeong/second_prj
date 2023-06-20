package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dto.Mem;
import dto.OrderView;
import service.ShopService;

@Controller
@RequestMapping("mypage")
public class MypageController {
	@Autowired
	private ShopService service;
	
	@GetMapping("*")
	public ModelAndView all() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@GetMapping("myInfo")
	public ModelAndView myInfo(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Mem mem = (Mem) session.getAttribute("loginMem");
		mav.addObject("mem", mem);
		return mav;
	}
	
	@GetMapping("orderList")
	public ModelAndView orderList(HttpSession session) {
		ModelAndView mav = new ModelAndView();		
		Mem mem = (Mem) session.getAttribute("loginMem");
		List<OrderView> ov = service.getOv(mem.getMem_id());
		Map<String, List<OrderView>> map = new HashMap<>();
		List<Integer> priceList = new ArrayList<>();
		for(OrderView o : ov) {
			List<OrderView> ovList = service.getOvList(mem.getMem_id(), o.getOrder_id());
			map.put(o.getOrder_id(), ovList);
		}
		mav.addObject("ov", ov);
		mav.addObject("orderCnt", ov.size());
		mav.addObject("map",map);
		return mav;
	}
}
