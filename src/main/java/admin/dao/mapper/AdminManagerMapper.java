package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Manager;

public interface AdminManagerMapper {

	@Insert("insert into manager (manager_id, manager_pass, manager_name, manager_birth, manager_grant) "
			+ " values (#{manager_id}, #{manager_pass}, #{manager_name}, #{manager_birth}, #{manager_grant})")
	boolean managerReg(Manager manager);

	@Select("select count(*) from manager where manager_name like '%${value}%'")
	int managerCnt(String query);

	@Select("select * from manager where manager_name like '%${query}%' ORDER BY manager_number DESC LIMIT #{start}, 10 ")
	List<Manager> getManagerList(Map<String, Object> param);

	@Select({"<script>",
		" select * from manager where manager_id = #{manager_id} ",
		" <if test='manager_pass != null'> and manager_pass = #{manager_pass} </if> ",
		" </script>"})
	Manager getManager(Map<String, Object> param);

}
