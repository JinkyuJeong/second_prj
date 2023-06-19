package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CartDao;
import dao.OptDao;
import dao.ProductDao;
import dao.QnaDao;
import dto.Cart;
import dto.Opt;
import dto.Product;
import dto.ProductOptView;
import dto.Qna;
@Service
public class ShopService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OptDao optDao;
	
	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private QnaDao qnaDao;

	public List<Product> productList(Integer pageNum, int limit, String product_type, String searchContent) {
		return productDao.list(pageNum, limit, product_type, searchContent);
	}

	public int productCount(String product_type, String searchContent) {
		return productDao.count(product_type, searchContent);
	}

	public Product getProduct(Integer product_number) {
		return productDao.getProduct(product_number);
	}

	public List<Opt> getOption(int product_number) {
		return optDao.getOption(product_number);
	}

	public Opt getOptionByNum(Integer opt_number) {
		return optDao.getOptionByNum(opt_number);
	}

	public boolean addCart(String mem_id, String optionNumber, String optionCount) {
		Cart cart = cartDao.getCart(mem_id, optionNumber);		
		if(cart != null) {
			String ogCount = cart.getOpt_count(); //기존 개수
			cartDao.updateCount(mem_id, optionNumber, optionCount, ogCount);
			return true;
		} else {
			return cartDao.addCart(mem_id, optionNumber, optionCount);
		}
	}

	public List<Cart> getCartList(String mem_id) {
		return cartDao.getCartList(mem_id);
	}

	public ProductOptView getProductOptView(int opt_number) {
		return optDao.getProductOptView(opt_number);
	}

	public int getQnaCnt(String type) {
		return qnaDao.getQnaCnt(type);
	}

	public List<Qna> getQnaList(Integer pageNum, String type) {
		return qnaDao.getQnaList(pageNum, type);
	}

	public Qna getQna(Integer qna_number) {
		return qnaDao.getQna(qna_number);
	}

	public void addQnaHits(Integer qna_number) {
		qnaDao.addQnaHits(qna_number);
	}
}
