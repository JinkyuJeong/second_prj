package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.RefundMapper;
import dto.Refund;

@Repository
public class RefundDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<RefundMapper> cls = RefundMapper.class;
	
	public void addRefund(String order_id, int optId, String mem_id, int price) {
		param.clear();
		param.put("order_id", order_id);
		param.put("optId", optId);
		param.put("mem_id", mem_id);
		param.put("price", price);
		template.getMapper(cls).addRefund(param);
	}

	public List<Refund> getRefundList(String mem_id) {
		return template.getMapper(cls).getRefundList(mem_id);
	}

	public List<Refund> getRefundCancelList(String mem_id, String refund_type) {
		param.clear();
		param.put("mem_id", mem_id);
		param.put("refund_type", refund_type);
		return template.getMapper(cls).getRefundCancelList(param);
	}
}
