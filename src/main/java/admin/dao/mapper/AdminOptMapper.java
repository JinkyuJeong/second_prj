package admin.dao.mapper;

import org.apache.ibatis.annotations.Insert;

import dto.Opt;

public interface AdminOptMapper {

	@Insert("insert into opt (opt_name, opt_quantity, product_number) values (#{opt_name}, #{opt_quantity}, #{product_number})")
	boolean regProductOpt(Opt opt);

}
