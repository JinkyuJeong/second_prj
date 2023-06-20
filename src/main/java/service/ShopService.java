package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CartDao;
import dao.DeliveryDao;
import dao.MemDao;
import dao.OptDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.ProductDao;
import dao.QnaDao;
import dto.Cart;
import dto.Delivery;
import dto.Mem;
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
	private MemDao memDao;
	
	@Autowired
	private DeliveryDao deliveryDao;
	
	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderItemDao orderItemDao;

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

	public boolean userInsert(Mem mem) {
		return memDao.userInsert(mem);
	}

	public Mem getMemEmail(String email) {
		return memDao.getMemEmail(email);
	}

	public int maxMemNum() {
		return memDao.maxMemNum();
	}

	public boolean cartDelete(Integer opt_number, String mem_id) {
		return cartDao.cartDelete(opt_number, mem_id);
	}

	public boolean cartMinus(Integer opt_number, String mem_id) {
		return cartDao.cartMinus(opt_number, mem_id);
	}

	public boolean cartPlus(Integer opt_number, String mem_id) {
		return cartDao.cartPlus(opt_number, mem_id);
	}

	public Cart getCart(String mem_id, Integer opt_number) {
		return cartDao.getCart(mem_id, opt_number);
	}

	public boolean addDelivery(Delivery delivery, String mem_id) {
		return deliveryDao.addDelivery(delivery, mem_id);
	}

	public List<Delivery> getDeliveryList(String mem_id) {
		return deliveryDao.getDeliveryList(mem_id);
	}

	public Delivery getDelivery(Integer selectedOption) {
		return deliveryDao.getDelivery(selectedOption);
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

	public String getMaxOrderId() {
		return orderDao.getMaxOrderId();
	}

	public boolean addOrder(String order_id, String deliver_receiver, String mem_id, String delivery_postcode,
			String delivery_address, String delivery_detailAddress, int delivery_cost, int order_point, String phoneno, String order_msg) {
		return orderDao.addOrder(order_id, deliver_receiver, mem_id, delivery_postcode, delivery_address, delivery_detailAddress, delivery_cost, order_point, phoneno, order_msg);
	}

	public boolean addOrderItem(String order_id, Integer opt_number, Integer product_number, String opt_count) {
		return orderItemDao.addOrderItem(order_id, opt_number, product_number, opt_count);
	}

}
