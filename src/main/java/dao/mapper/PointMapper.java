package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Point;

public interface PointMapper {
	@Insert("insert into point (mem_id, point_type, point_value, point_regdate) values (#{value}, '회원가입', 2000, now())")
	void pointInsert(String mem_id);

	@Insert("insert into point (mem_id, point_type, point_value, point_regdate) values (#{mem_id}, '포인트 사용', #{order_point}, now())")
	void pointUsedStore(Map<String, Object> param);

	@Select("select * from point where mem_id=#{mem_id}")
	List<Point> getMyPoint(String mem_id);

	@Select("select * from point where mem_id=#{mem_id} and point_type != #{point_type}")
	List<Point> getMyPointReceived(Map<String, Object> param);

	@Select("select * from point where mem_id=#{mem_id} and point_type = #{point_type}")
	List<Point> getMyPointUsed(Map<String, Object> param);
	
	@Insert("insert into point (mem_id, point_type, point_value, point_regdate) values(#{mem_id}, '포인트 환불', #{order_point}, now())")
	void pointBack(Map<String, Object> param);

}
