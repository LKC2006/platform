package com.tradeplatform.mapper;



import com.tradeplatform.pojo.ProductOrder;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductOrderMapper {

    @Select("select * from product_order")
    public List<ProductOrder> list();

    @Delete("delete from product_order where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    @Insert("insert into product_order(product_id, buyer_id, status, create_time, update_time) " +
            "VALUES (#{product_id}, #{buyer_id}, #{status}, #{create_time},#{update_time})")
    public void insert(ProductOrder productOrder);

}
