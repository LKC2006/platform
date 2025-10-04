package com.tradeplatform.service;

import com.tradeplatform.mapper.ProductMapper;
import com.tradeplatform.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
//按"类型（二手物品/代取需求）、价格范围、发布时间（如"24小时内新发布"）"筛选列表。
//实现类
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer selectUserId(Integer productId) throws SQLException {
        return productMapper.selectUserId(productId);
    }

    @Override
    public String selectRole(Integer id) throws SQLException {

        return productMapper.selectRole(id);
    }


    @Override
    public List<Product> selectNear() throws SQLException {
        try{
            return productMapper.selectNear();
        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> selectPrice(BigDecimal price1, BigDecimal price2) throws SQLException {
        List<Product> productList;
        try {
            productList= productMapper.selectPrice(price1,price2);

        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(productList.size() == 0||productList== null){
            throw new RuntimeException("There Is NO Product");
        }//没有产品时返回提示到前端

        return productList;
    }

    @Override
    public List<Product> dynamicSelect(Product product) throws SQLException{
        try{
            return productMapper.dynamicSelect(product);//再次传入product
        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> list() throws SQLException {
        List<Product> productList;
        try {
            productList= productMapper.list();

        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(productList.size() == 0||productList== null){
            throw new RuntimeException("There Is NO Product");
        }//没有产品时返回提示到前端

        return productList;
    }

    @Override
    public List<Product> selectByTitle(String title) throws SQLException {
        List<Product> productList;
        try{
            productList = productMapper.selectByTitle(title);
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(productList == null||productList.size() == 0){
            throw new RuntimeException("The Product With The Title " + title + " Doesn't Exist");
        }
        return productList;
    }

    @Override
    public Product selectById(Integer id) throws SQLException {
        Product product;
        try{
            product = productMapper.selectById(id);
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public List<String> getTitle() throws SQLException {
        List<String> productTitle;
        try{
            productTitle = productMapper.getTitle();
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productTitle;
    }

    @Override
    public int delete(Integer id) throws SQLException {
        int result;
        try{
            result = productMapper.delete(id);
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    @Override
    public int insert(Product product) throws SQLException {
        if((product.getType()==null||!product.getType().equals("二手物品")&&!product.getType().equals("代取需求"))) {
            throw new IllegalArgumentException("种类只能为“二手物品”或者“代取需求”");
        }
        if((product.getStatus()==null||!product.getStatus().equals("未售出")&&!product.getStatus().equals("已售出"))) {
            throw new IllegalArgumentException("状态只能为“未售出”或者“已售出”");
        }
        int result = product.getPrice().compareTo(BigDecimal.valueOf(0));
        if(result<=0) {
            throw new IllegalArgumentException("价格不能为负数");
        }//表示比0小
        int amount;
        try{
            amount = productMapper.insert(product);
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return amount;
    }

    @Override
    public int update(Product product) throws SQLException {
        int amount;
        if((product.getType()==null||!product.getType().equals("二手物品")&&!product.getType().equals("代取需求"))) {
            throw new IllegalArgumentException("种类只能为“二手物品”或者“代取需求”");
        }
        if((product.getStatus()==null||!product.getStatus().equals("未售出")&&!product.getStatus().equals("已售出"))) {
            throw new IllegalArgumentException("状态只能为“未售出”或者“已售出”");
        }
        int result = product.getPrice().compareTo(BigDecimal.valueOf(0));
        if(result<=0) {
            throw new IllegalArgumentException("价格不能为负数");
        }//表示比0小
        try{
            amount = productMapper.update(product);
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return amount;
    }
}
