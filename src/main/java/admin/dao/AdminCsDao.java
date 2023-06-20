package admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import admin.dao.mapper.AdminCsMapper;
import dto.Cs;

@Repository
public class AdminCsDao {

	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<AdminCsMapper> cls = AdminCsMapper.class;
	
	public int csCnt(String sd, String ed) {
		param.clear();
		param.put("sd", sd);
		param.put("ed", ed);
		
		return template.getMapper(cls).csCnt(param);
	}

	public List<Cs> getCsList(Integer pageNum, String sd, String ed) {
		param.clear();
		param.put("sd", sd);
		param.put("ed", ed);
		param.put("start", (pageNum -1) * 10);
		param.put("limit", 10);
		
		return template.getMapper(cls).getCsList(param);
	}
}
