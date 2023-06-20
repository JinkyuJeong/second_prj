package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import dto.Cs;

public interface AdminCsMapper {

	@Select({"<script> ",
		" select count(*) from cs where mem_id like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND cs_qdate &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND cs_qdate &lt;= #{ed} ",
		" </if>",
		" <if test='cs_state != null and cs_state != \"\"'> ",
		"  AND cs_state = #{cs_state} ",
		" </if>",
	" </script>"})
	int csCnt(Map<String, Object> param);

	@Select({"<script> ",
		"SELECT * FROM cs where mem_id like '%${query}%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND cs_qdate &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND cs_qdate &lt;= #{ed} ",
		" </if>",
		" <if test='cs_state != null and cs_state != \"\"'> ",
		"  AND cs_state = #{cs_state} ",
		" </if>",
		" ORDER BY cs_number DESC LIMIT #{start}, 10",
		"</script>"
	})	
	List<Cs> getCsList(Map<String, Object> param);

	
}
