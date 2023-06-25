package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dto.Order;
import dto.OrderView;

public interface AdminOrderMapper {

	@Select({"<script> ",
		" select count(*) from h_order where ${f} like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND order_date &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND order_date &lt;= #{ed} ",
		" </if>",
		" <if test='order_state != null and order_state != \"\"'> ",
		"  AND order_state = #{order_state} ",
		" </if>",
	" </script>"})
	int orderCnt(Map<String, Object> param);

	@Select({"<script> ",
		"SELECT * FROM h_order where ${f} like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND order_date &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND order_date &lt;= #{ed} ",
		" </if>",
		" <if test='order_state != null and order_state != \"\"'> ",
		"  AND order_state = #{order_state} ",
		" </if>",
		" ORDER BY order_id DESC LIMIT #{start}, 10",
		"</script>"
	})	
	List<Order> getOrderList(Map<String, Object> param);

	@Select("select * from h_order where order_id=#{value}")
	Order getOrder(String order_id);

	@Select("select * from orderView where order_id = #{value}")
	List<OrderView> getOrderItem(String order_id);

	@Update("update h_order set order_state=#{state} where order_id=#{order_id}")
	void orderStateChg(Map<String, Object> param);

}
