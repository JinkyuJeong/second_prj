package dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {

	@Select("select ifnull(max(order_id), 1) from h_order")
	String getMaxOrderId();

	@Insert("insert into h_order (order_id, order_receiver, mem_id, delivery_postcode, delivery_address, delivery_detailAddress, order_state, delivery_cost, order_point, order_date, order_phoneno, order_msg)"
			+ " values (#{order_id}, #{deliver_receiver}, #{mem_id}, #{delivery_postcode}, #{delivery_address}, #{delivery_detailAddress}, '결제완료', #{delivery_cost}, #{order_point}, now(), #{phoneno}, #{order_msg})")
	boolean addOrder(Map<String, Object> param);
}
