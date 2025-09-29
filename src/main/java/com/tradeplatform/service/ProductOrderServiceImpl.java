package com.tradeplatform.service;

import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.pojo.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductOrderServiceImpl implements ProductOrderMapper {

    @Autowired
    private ProductOrderMapper productOrderMapper;

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
