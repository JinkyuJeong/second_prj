package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Refund;

public interface RefundMapper {

	@Insert("insert into refund (refund_orderId, refund_optId, refund_memId, refund_reason, refund_price, refund_type) "
			+ " values (#{order_id}, #{optId}, #{mem_id}, '사용자 주문취소', #{price}, '취소')")
	void addRefund(Map<String, Object> param);

	@Select("select * from refund where refund_memId = #{value}")
	List<Refund> getRefundList(String mem_id);

	@Select("select * from refund where refund_memId = #{mem_id} and refund_type = #{refund_type}")
	List<Refund> getRefundCancelList(Map<String, Object> param);

}
