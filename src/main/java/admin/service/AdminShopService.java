package admin.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import admin.dao.AdminOptDao;
import admin.dao.AdminProductDao;
import dto.Opt;
import dto.Product;

@Service
public class AdminShopService {
	
	@Autowired
	private AdminProductDao productDao;
	@Autowired
	private AdminOptDao optDao;

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
	
	public boolean regProduct(Product product, HttpServletRequest request) {
		List<MultipartFile> list = product.getPicFiles();
		String path = request.getServletContext().getRealPath("/") + "img/product/";
		if(list != null) {
			for(MultipartFile file : list) {
				if(!file.isEmpty()) {
					uploadFileCreate(file, path);
				}
			}
		}
		String thumbPath = request.getServletContext().getRealPath("/") + "img/thumb/";
		if(product.getThumbFile() != null && !product.getThumbFile().isEmpty()) {
			uploadFileCreate(product.getThumbFile(), thumbPath);
		}
		return productDao.regProduct(product);
	}

	public boolean updateProduct(Product product, HttpServletRequest request) {
		List<MultipartFile> list = product.getPicFiles();
		String path = request.getServletContext().getRealPath("/") + "img/product/";
		if(list != null) {
			for(MultipartFile file : list) {
				if(!file.isEmpty()) {
					uploadFileCreate(file, path);
				}
			}
		}
		String thumbPath = request.getServletContext().getRealPath("/") + "img/thumb/";
		if(product.getThumbFile() != null && !product.getThumbFile().isEmpty()) {
			uploadFileCreate(product.getThumbFile(), thumbPath);
		}
		return productDao.updateProduct(product);
	}
	
	public List<Product> getProdList(Integer pageNum, String query) {
		return productDao.getProdList(pageNum, query);
	}

	public int getProdCnt(Integer pageNum, String query) {
		return  productDao.getProdCnt(pageNum, query);
	}

	public Product getProd(Integer product_number) {
		return productDao.getProd(product_number);
	}
	
	public boolean deleteProduct(int product_number) {
		return productDao.deleteProduct(product_number);
	}
	
	public boolean regProductOpt(Opt opt) {
		return optDao.regProductOpt(opt);
	}

}
