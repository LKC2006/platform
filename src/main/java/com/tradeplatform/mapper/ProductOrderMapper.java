package com.tradeplatform.mapper;



import com.tradeplatform.pojo.ProductOrder;

import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface ProductOrderMapper {

//需要操作多表，用xml配置
    public void cancelOrder(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public void rollBackStatus(@Param("productid") Integer productid) throws SQLException;

    public void placeOrder(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public void alterStatus(@Param("buyerid") Integer buyerid,@Param("productid") Integer productid) throws SQLException;
    public String getStatusForOrder(@Param("productid") Integer productid) throws SQLException;

    //查询所有订单
    @Select("select * from product_order")
    public List<ProductOrder> list();
}
