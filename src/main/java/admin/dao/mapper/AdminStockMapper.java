package admin.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import dto.Stock;

public interface AdminStockMapper {

	@Insert("insert into stock (opt_number, stock_regdate, stock_quantity, stock_prodName, stock_prodThumb) values (#{opt_number}, now(), #{stock_quantity}, #{stock_prodName}, #{stock_prodThumb})")
	boolean regProdStock(Stock stock);

	@Select("select count(*) from stock  where stock_prodName like '%${value}%'")
	int getStockCnt(String query);

	@Select("select * from stock where stock_prodName like '%${query}%' ORDER BY stock_number DESC LIMIT #{start}, 10")
	List<Stock> getStockList(Map<String, Object> param);

	@Select("select * from stock where stock_number = #{value}")
	Stock getStock(Integer stock_number);

	@Update("update stock set stock_quantity = #{stock_quantity} where stock_number = #{stock_number}")
	boolean updateStock(Stock stock);

}
