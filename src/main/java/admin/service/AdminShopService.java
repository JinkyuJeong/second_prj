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
import admin.dao.AdminStockDao;
import dto.Opt;
import dto.Product;
import dto.ProductOptView;
import dto.Stock;

@Service
public class AdminShopService {
	
	@Autowired
	private AdminProductDao productDao;
	@Autowired
	private AdminOptDao optDao;
	@Autowired
	private AdminStockDao stockDao;

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

	public int getProdCnt(String query) {
		return  productDao.getProdCnt(query);
	}

	public Product getProd(Integer product_number) {
		return productDao.getProd(product_number);
	}
	
	public boolean deleteProduct(Integer product_number) {
		return productDao.deleteProduct(product_number);
	}
	
	public boolean regProductOpt(Opt opt) {
		return optDao.regProductOpt(opt);
	}

	public List<ProductOptView> getOptList(Integer pageNum, String query) {
		return optDao.getOptList(pageNum, query);
	}

	public int getOptCnt(String query) {
		return optDao.getOptCnt(query);
	}

	public ProductOptView getProdOpt(Integer opt_number) {
		return optDao.getProdOpt(opt_number);
	}

	public boolean updateOpt(Opt opt) {
		return optDao.updateOpt(opt);
	}
	
	public boolean deleteOpt(Integer opt_number) {
		return optDao.deleteOpt(opt_number);
	}
	
	@Transactional
	public boolean regProdStock(Stock stock) {
		boolean b1 = stockDao.regProdStock(stock);
		boolean b2 = optDao.updateQuantity(stock.getOpt_number() ,stock.getStock_quantity());
		return b1 && b2;
	}

	public int getStockCnt(String query) {
		return stockDao.getStockCnt(query);
	}

	public List<Stock> getStockList(Integer pageNum, String query) {
		return stockDao.getStockList(pageNum, query);
	}

}
