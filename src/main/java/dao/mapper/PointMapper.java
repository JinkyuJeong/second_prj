package dao.mapper;

import org.apache.ibatis.annotations.Insert;

public interface PointMapper {
	@Insert("insert into point (mem_id, point_type, point_value, point_regdate) values (#{value}, '회원가입', 2000, now())")
	void pointInsert(String mem_id);

}
