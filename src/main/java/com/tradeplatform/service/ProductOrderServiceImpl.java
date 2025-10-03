package com.tradeplatform.service;

import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.pojo.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Override
    public void cancelOrder(Integer buyerid, Integer productid) throws SQLException {
        String status = productOrderMapper.getStatusForOrder(productid);
        if(status.equals("已售出")) {
            productOrderMapper.cancelOrder(buyerid, productid);
            return;
        }
        throw new RuntimeException("Order Not In Progress");
    }

    @Override
    public void rollBackStatus(Integer productid) throws SQLException {
        productOrderMapper.rollBackStatus(productid);
    }

    @Override
    public void placeOrder(Integer buyerid, Integer productid) throws SQLException {
        String status = productOrderMapper.getStatusForOrder(productid);
        if(status.equals("未售出")) {
            productOrderMapper.placeOrder(buyerid, productid);
            return;
        }//还没卖掉才能买
        else {
            throw new RuntimeException("Product Already Sold");
        }
    }

    @Override
    public void alterStatus(Integer buyerid, Integer productid) throws SQLException {
        productOrderMapper.alterStatus(buyerid, productid);
        //这个在xml文件中比较容易核验
    }

    @Override
    public List<ProductOrder> list() {
        return productOrderMapper.list();
    }

    @Override
    public void delete(Integer id) {
         productOrderMapper.delete(id);
    }

    @Override
    public void insert(ProductOrder productOrder) {
        productOrderMapper.insert(productOrder);

    }
}
