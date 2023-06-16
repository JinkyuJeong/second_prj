package admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import admin.dao.mapper.AdminStockMapper;
import dto.Stock;

@Repository
public class AdminStockDao {

	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	private final Class<AdminStockMapper> cls = AdminStockMapper.class;
	
	public boolean regProdStock(Stock stock) {
		return template.getMapper(cls).regProdStock(stock);
	}

	public int getStockCnt(String query) {
		return template.getMapper(cls).getStockCnt(query);
	}

	public List<Stock> getStockList(Integer pageNum, String query) {
		param.clear();
		param.put("query", query);
		param.put("start", (pageNum -1) * 10);
		param.put("limit", 10);
		
		return template.getMapper(cls).getStockList(param);
	}
}
