package dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import dto.Opt;
import dto.ProductOptView;

public interface OptMapper {
	@Select("select * from opt where product_number=#{value}")
	List<Opt> getOption(int product_number);

	@Select("select * from opt where opt_number=#{value}")
	Opt getOptionByNum(Integer opt_number);

	@Select("select * from productOptView where opt_number=#{value}")
	ProductOptView getProductOptView(int opt_number);

}
