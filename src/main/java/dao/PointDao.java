package dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.OrderItemMapper;
import dao.mapper.PointMapper;

@Repository
public class PointDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<PointMapper> cls = PointMapper.class;
	
	public void pointInsert(String mem_id) {
		template.getMapper(cls).pointInsert(mem_id);
	}
	

}
