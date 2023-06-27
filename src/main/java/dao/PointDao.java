package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.OrderItemMapper;
import dao.mapper.PointMapper;
import dto.Point;

@Repository
public class PointDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<PointMapper> cls = PointMapper.class;
	
	public void pointInsert(String mem_id) {
		template.getMapper(cls).pointInsert(mem_id);
	}

	public void pointUsedStore(Integer order_point, String mem_id) {
		param.clear();
		param.put("order_point", order_point);
		param.put("mem_id", mem_id);
		template.getMapper(cls).pointUsedStore(param);
	}

	public List<Point> getMyPoint(String mem_id) {
		return template.getMapper(cls).getMyPoint(mem_id);
	}

	public List<Point> getMyPointReceived(String mem_id, String point_type) {
		param.clear();
		param.put("mem_id", mem_id);
		param.put("point_type", point_type);
		return template.getMapper(cls).getMyPointReceived(param);
	}

	public List<Point> getMyPointUsed(String mem_id, String point_type) {
		param.clear();
		param.put("mem_id", mem_id);
		param.put("point_type", point_type);
		return template.getMapper(cls).getMyPointUsed(param);
	}

	public void pointBack(String mem_id, int order_point) {
		param.clear();
		param.put("mem_id", mem_id);
		param.put("order_point", order_point);
		template.getMapper(cls).pointBack(param);
	}	

}
