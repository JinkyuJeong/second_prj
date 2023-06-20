package dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.OrderMapper;

@Repository
public class OrderDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<OrderMapper> cls = OrderMapper.class;
	
	public String getMaxOrderId() {
		return template.getMapper(cls).getMaxOrderId();
	}

	public boolean addOrder(String order_id, String deliver_receiver, String mem_id, String delivery_postcode,
			String delivery_address, String delivery_detailAddress, int delivery_cost, int order_point, String phoneno, String order_msg) {
		param.clear();
		param.put("order_id", order_id);
		param.put("deliver_receiver", deliver_receiver);
		param.put("mem_id", mem_id);
		param.put("delivery_postcode", delivery_postcode);
		param.put("delivery_address", delivery_address);
		param.put("delivery_detailAddress", delivery_detailAddress);
		param.put("phoneno", phoneno);
		param.put("order_msg", order_msg);
		param.put("delivery_cost", delivery_cost);
		param.put("order_point", order_point);
		return template.getMapper(cls).addOrder(param);
	}
}
