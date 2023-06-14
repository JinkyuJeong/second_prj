package admin.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import admin.dao.mapper.AdminProductMapper;
import dto.Product;

@Repository
public class AdminProductDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<AdminProductMapper> cls = AdminProductMapper.class;
	
//	public int maxNumber() {
//		return template.getMapper(cls).maxNumber();
//	}
	public void regProduct(Product product) {
		template.getMapper(cls).regProduct(product);
	}
}
