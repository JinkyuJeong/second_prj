package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dto.Order;
import dto.OrderView;

public interface OrderMapper {

	@Select("select ifnull(max(order_id), 1) from h_order")
	String getMaxOrderId();

	@Insert("insert into h_order (order_id, order_receiver, mem_id, delivery_postcode, delivery_address, delivery_detailAddress, order_state, delivery_cost, order_point, order_date, order_phoneno, order_msg, order_totalPay)"
			+ " values (#{order_id}, #{deliver_receiver}, #{mem_id}, #{delivery_postcode}, #{delivery_address}, #{delivery_detailAddress}, '결제완료', #{delivery_cost}, #{order_point}, now(), #{phoneno}, #{order_msg}, #{order_totalPay})")
	boolean addOrder(Map<String, Object> param);

	@Select("select * from h_order where mem_id=#{value}")
	List<Order> getOrderList(String mem_id);

	@Select("select * from orderView where mem_id=#{value} order by order_date desc")
	List<OrderView> getOv(String mem_id);

	@Select("select * from orderView where mem_id=#{mem_id} and order_id=#{order_id} order by order_date desc")
	List<OrderView> getOvList(Map<String, Object> param);

	@Update("update h_order set order_state = '주문취소' where order_id = #{value}")
	void updateOrderState(String order_id);
}
