package admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import admin.dao.AdminOrderDao;
import dto.Order;
import dto.OrderView;

@Service
public class AdminOrderService {
	
	@Autowired
	private AdminOrderDao orderDao;

	public int orderCnt(String f, String query, String sd, String ed, String order_state) {
		return orderDao.orderCnt(f, query, sd, ed, order_state);
	}

	public List<Order> getOrderList(Integer pageNum, String f, String query, String sd, String ed, String order_state) {
		return orderDao.getOrderList(pageNum, f, query, sd, ed, order_state);
	}

	public Order getOrder(String order_id) {
		return orderDao.getOrder(order_id);
	}

	public List<OrderView> getOrderItem(String order_id) {
		return orderDao.getOrderItem(order_id);
	}

	public void orderStateChg(String order_id, String state) {
		orderDao.orderStateChg(order_id, state);
	}

}
