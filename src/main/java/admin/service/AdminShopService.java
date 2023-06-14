package admin.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import admin.dao.AdminProductDao;
import dto.Product;

@Service
public class AdminShopService {
	
	@Autowired
	private AdminProductDao productDao;

	public void uploadFileCreate(MultipartFile file, String path) {
		String orgFile = file.getOriginalFilename();
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		try {
			file.transferTo(new File(path+orgFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void regProduct(Product product, HttpServletRequest request) {
		List<MultipartFile> list = product.getPicFiles();
		String path = request.getServletContext().getRealPath("/") + "img/product/";
		if(list != null) {
			for(MultipartFile file : list) {
				if(!file.isEmpty()) {
					uploadFileCreate(file, path);
				}
			}
		}
		
		if(product.getThumbFile() != null && !product.getThumbFile().isEmpty()) {
			uploadFileCreate(product.getThumbFile(), path);
		}
		
//		int maxNumber = productDao.maxNumber();
//		product.setProduct_number(maxNumber+1);
		productDao.regProduct(product);
	}
}
