package com.tradeplatform.mapper;



import com.tradeplatform.pojo.ProductOrder;

import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ProductOrderMapper {


    public void cancelOrder(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public void rollBackStatus(@Param("productid") Integer productid) throws SQLException;

    public void placeOrder(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public void alterStatus(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public String getStatusForOrder(@Param("productid") Integer productid) throws SQLException;

    @Select("select * from product_order")
    public List<ProductOrder> list();

    @Delete("delete from product_order where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    @Insert("insert into product_order(product_id, buyer_id, status, create_time, update_time) " +
            "VALUES (#{product_id}, #{buyer_id}, #{status}, #{create_time},#{update_time})")
    public void insert(ProductOrder productOrder);

}
