package admin.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import admin.dao.mapper.AdminOptMapper;
import dto.Opt;

@Repository
public class AdminOptDao {

	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<AdminOptMapper> cls = AdminOptMapper.class;
	
	public boolean regProductOpt(Opt opt) {
		return template.getMapper(cls).regProductOpt(opt);
	}
}
