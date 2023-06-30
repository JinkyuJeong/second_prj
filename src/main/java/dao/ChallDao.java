package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.ChallMapper;
import dto.Chall;

@Repository
public class ChallDao {

	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<ChallMapper> cls = ChallMapper.class;
	
	public int maxNum() {
		return template.getMapper(cls).maxNum();
	}
	
	public boolean regChall(Chall chall) {
		return template.getMapper(cls).regChall(chall);
	}

	public int getChallCnt() {
		return template.getMapper(cls).getChallCnt();
	}

	public List<Chall> getChallList(Integer pageNum) {
		param.clear();
		param.put("start", (pageNum -1) * 10);
		param.put("limit", 10);
		
		return template.getMapper(cls).getChallList(param);
	}

	public Chall getChall(String mem_id, String chall_regdate) {
		param.clear();
		param.put("mem_id", mem_id);
		param.put("chall_regdate", chall_regdate);
		
		return template.getMapper(cls).getChall(param);
	}

	public int challCnt(String mem_id) {
		return template.getMapper(cls).challCnt(mem_id);
	}

	public Chall getMyChall(String mem_id) {
		return template.getMapper(cls).getMyChall(mem_id);
	}

}
