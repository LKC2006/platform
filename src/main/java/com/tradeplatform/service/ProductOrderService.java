package com.tradeplatform.service;

import com.tradeplatform.pojo.ProductOrder;

import java.sql.SQLException;
import java.util.List;

public interface ProductOrderService {

    public void cancelOrder(Integer buyerid,Integer productid) throws SQLException;
    public void rollBackStatus(Integer productid) throws SQLException;


    public void placeOrder(Integer buyerid,Integer productid) throws SQLException;
    public void alterStatus(Integer buyerid,Integer productid) throws SQLException;

    public List<ProductOrder> list();
}
