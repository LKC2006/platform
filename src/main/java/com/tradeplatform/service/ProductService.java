package com.tradeplatform.service;

import com.tradeplatform.pojo.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    public List<Product> selectNear() throws SQLException;
    public List<Product> selectPrice(BigDecimal price1,BigDecimal price2) throws SQLException;
    public List<Product> dynamicSelect(Product product) throws SQLException;
    public List<Product> list() throws SQLException;
    public List<Product> selectByTitle(String title) throws SQLException;
    public Product selectById(Integer id) throws SQLException;
    public List<String> getTitle() throws SQLException;
    public int delete(Integer id) throws SQLException;
    public int insert(Product product) throws SQLException;
    public int update(Product product) throws SQLException;
}
