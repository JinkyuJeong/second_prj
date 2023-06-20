package dao.mapper;

import org.apache.ibatis.annotations.Insert;

import dto.Cs;

public interface CsMapper {

	@Insert("insert into cs (mem_id, cs_qContent, cs_state) values (#{mem_id}, #{cs_qContent}, '답변대기')")
	boolean csReg(Cs cs);

}
