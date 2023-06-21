package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dto.Point;

public interface AdminPointMapper {

	@Insert("insert into point (mem_id, point_type, point_value, point_regdate) values(#{mem_id}, #{point_type}, #{point_value}, now())")
	boolean regPoint(Point point);

	@Select("select count(*) from point  where mem_id like '%${value}%'")
	int getPointCnt(String query);

	@Select("select * from point where mem_id like '%${query}%' ORDER BY point_number DESC LIMIT #{start}, 10")
	List<Point> getPointList(Map<String, Object> param);

	@Select("select * from point where point_number=#{value}")
	Point getPoint(Integer point_number);

	@Update("update point set point_value=#{point_value}, point_type=#{point_type} where point_number=#{point_number}")
	boolean pointChg(Point point);

	@Delete("delete from point where point_number=#{point_number}")
	boolean pointDel(Integer point_number);

}
