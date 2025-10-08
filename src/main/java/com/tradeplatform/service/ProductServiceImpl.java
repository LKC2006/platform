package com.tradeplatform.service;

import com.tradeplatform.mapper.ProductMapper;
import com.tradeplatform.pojo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
//按"类型（二手物品/代取需求）、价格范围、发布时间（如"24小时内新发布"）"筛选列表。
//实现类
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    //辅助方法，根据产品返回用户，给其他方法调用
    @Override//表示实现类重写接口方法
    @Cacheable(value = "productCache", key = "'allProducts'")//缓存
    public Integer selectUserId(Integer productId) throws SQLException {
        return productMapper.selectUserId(productId);
    }

    //辅助，根据用户ID返回角色，辅助鉴权
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
    public String selectRole(Integer id) throws SQLException {

        return productMapper.selectRole(id);
    }

    //24h以内的产品
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
    public List<Product> selectNear() throws SQLException {
        try{
            return productMapper.selectNear();
        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //根据价格区间查询
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
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

    //动态查询
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
    public List<Product> dynamicSelect(Product product) throws SQLException{
        try{
            return productMapper.dynamicSelect(product);//再次传入product
        }catch(SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //查询所有产品
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
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

    //根据输入标题查询详情
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
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

    //根据ID查询
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
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

    //查询列表（无详情）
    @Override
    @Cacheable(value = "productCache", key = "'allProducts'")
    public List<String> getTitle() throws SQLException {
        List<String> productTitle;
        try{
            log.info("Cache Failed To Cover");
            productTitle = productMapper.getTitle();
        }catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return productTitle;
    }

    //删除产品
    @Override
    @CacheEvict(value = "productCache", key = "'allProducts'")
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

    //新增产品
    @Override
    @CacheEvict(value = "productCache", key = "'allProducts'")
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

    //更新产品
    @Override
    @CacheEvict(value = "productCache", key = "'allProducts'")
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
