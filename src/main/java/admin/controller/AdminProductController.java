package admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import admin.service.AdminShopService;
import dto.Product;

@Controller
@RequestMapping("admin/product")
public class AdminProductController {
	
	@Autowired
	private AdminShopService service;
	
	@GetMapping("reg")
	public ModelAndView adminRegForm(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.addObject(new Product());
		return mv;
	}
	
	@PostMapping("reg")
	public ModelAndView adminRegProduct(Product product, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		service.regProduct(product, request);
		mv.setViewName("redirect:list");
		return mv;
	}
	
	@RequestMapping("imgupload")
	public String imgupload(MultipartFile upload, String CKEditorFuncNum, HttpServletRequest request, Model model) {
		String path=request.getServletContext().getRealPath("/")+"/upload/imgfile/";
		service.uploadFileCreate(upload, path);
		String fileName = request.getContextPath()+"/upload/imgfile/" + upload.getOriginalFilename();
		model.addAttribute("fileName", fileName);
		return "ckedit";
	}
}
