package admin.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import dto.Product;

public interface AdminProductMapper {

//	@Select("select ifnull(max(product_number),0) from product")
//	int maxNumber();

	@Insert("insert into product "
			+ " (product_name, product_type, product_price, product_desc, product_isDiscount, product_discountRate, product_regdate, product_thumb, product_pictures)"
			+ " values (#{product_name}, #{product_type}, #{product_price}, #{product_desc}, #{product_isDiscount}, #{product_discountRate}, now(), #{product_thumb}, #{product_pictures})")
	void regProduct(Product product);
	
}
