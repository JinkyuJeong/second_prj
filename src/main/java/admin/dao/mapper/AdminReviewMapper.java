package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import dto.ReviewView;

public interface AdminReviewMapper {

	@Select({"<script> ",
		" select count(*) from reviewView where ${f} like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND review_date &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND review_date &lt;= #{ed} ",
		" </if>",
		" <if test='review_state != null and review_state != \"\"'> ",
		"  AND review_state = #{review_state} ",
		" </if>",
	" </script>"})
	int reviewCnt(Map<String, Object> param);

	@Select({"<script> ",
		"SELECT * FROM reviewView where ${f} like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND review_date &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND review_date &lt;= #{ed} ",
		" </if>",
		" <if test='review_state != null and review_state != \"\"'> ",
		"  AND review_state = #{review_state} ",
		" </if>",
		" ORDER BY review_number DESC LIMIT #{start}, 10",
		"</script>"
	})	
	List<ReviewView> getReviewList(Map<String, Object> param);

}
