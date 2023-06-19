package dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Mem;

public interface MemMapper {
	@Insert("insert into mem (mem_number, mem_id, mem_pw, mem_channel, mem_name, mem_phoneno) values (#{mem_number}, #{mem_id}, #{mem_pw}, #{mem_channel}, #{mem_name}, #{mem_phoneno})")
	boolean userInsert(Mem mem);

	@Select("select * from mem where mem_id=#{value}")
	Mem getMemEmail(String email);
	
	@Select("select ifnull(max(mem_number), 0) from mem")
	int maxMemNum();

}
