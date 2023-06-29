package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Chall;

public interface ChallMapper {

	@Insert("insert into chall (chall_number, chall_regdate, mem_id, mem_name, chall_pic, chall_state) "
			+ " values(#{chall_number}, now(), #{mem_id}, #{mem_name}, #{chall_pic}, '지급대기')")
	boolean regChall(Chall chall);

	@Select("select ifnull(max(chall_number),0) from chall")
	int maxNum();

	@Select("select count(*) from chall")
	int getChallCnt();

	@Select("select * from chall ORDER BY chall_number DESC LIMIT #{start}, 8")
	List<Chall> getChallList(Map<String, Object> param);

	@Select("SELECT COUNT(*) from chall WHERE mem_id=#{value}")
	Integer dateCnt(String mem_id);

	@Select("select * from chall where mem_id=#{mem_id} and chall_regdate=#{chall_regdate}")
	Chall getChall(Map<String, Object> param);
}
