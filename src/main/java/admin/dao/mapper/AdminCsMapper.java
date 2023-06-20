package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import dto.Cs;

public interface AdminCsMapper {

	@Select({"<script> ",
		" select count(*) from cs where cs_qContent like '%%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND cs_qdate &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND cs_qdate &lt;= #{ed} ",
		" </if>",
	" </script>"})
	int csCnt(Map<String, Object> param);

	@Select({"<script> ",
		"SELECT * FROM cs where cs_qContent like '%%' ",
		" <if test='sd != null and sd != \"\"'>",
		"  AND cs_qdate &gt;= #{sd} ",
		" </if>",
		" <if test='ed != null and ed != \"\"'> ",
		"  AND cs_qdate &lt;= #{ed} ",
		" </if>",
		" ORDER BY cs_number DESC LIMIT #{start}, 10",
		"</script>"
	})	
	List<Cs> getCsList(Map<String, Object> param);

	
}
