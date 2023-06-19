package dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.MemMapper;
import dto.Mem;

@Repository
public class MemDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<MemMapper> cls = MemMapper.class;
	
	public boolean userInsert(Mem mem) {
		return template.getMapper(cls).userInsert(mem);
	}

	public Mem getMemEmail(String email) {
		return template.getMapper(cls).getMemEmail(email);
	}

	public int maxMemNum() {
		return template.getMapper(cls).maxMemNum();
	}
}
