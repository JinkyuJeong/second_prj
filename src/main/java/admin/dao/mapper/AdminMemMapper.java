package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import dto.Delivery;
import dto.Mem;

public interface AdminMemMapper {

	@Select("select count(*) from mem where ${f} like '%${query}%'")
	int memCnt(Map<String, Object> param);

	@Select("select * from mem where ${f} like '%${query}%' ORDER BY mem_number DESC LIMIT #{start}, 10 ")
	List<Mem> getMemList(Map<String, Object> param);

	@Select("select * from mem where mem_number=#{value}")
	Mem getMem(Integer mem_number);

	@Select("select * from delivery where mem_id=#{value}")
	List<Delivery> getDelList(String mem_id);

	@Delete("delete from mem where mem_number=#{value}")
	boolean memDel(Integer mem_number);

}
