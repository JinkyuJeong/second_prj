package dao.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Insert;

public interface OrderItemMapper {
	@Insert("insert into h_orderItem (order_id, product_number, opt_number, opt_count) values (#{order_id}, #{product_number}, #{opt_number}, #{opt_count})")
	boolean addOrderItem(Map<String, Object> param);
}
