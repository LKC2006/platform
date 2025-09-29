package com.tradeplatform.mapper;

import com.tradeplatform.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;
//仅负责数据访问,接口实现功能
@Mapper//数据访问层（dao）,自动创建接口的代理对象
public interface ProductMapper {//自动生成实现类对象ProductMapper.list()


    @Select("select * from product")//注解中指定要执行的SQL语句
    public List<Product> list() throws SQLException;//结果封装到方法的返回值中，mybatis自动完成

    //商品详情
    @Select("select * from product where title = #{title}")
    public List<Product> selectByTitle(String title) throws SQLException;

    @Select("select * from product where id = #{id}")
    public Product selectById(Integer id) throws SQLException;

    //查询商品列表
    @Select("select title from product")
    public List<String> getTitle() throws SQLException;

    @Delete("delete from product where id = #{id}")//利用参数占位符#{}
    public int delete(Integer id) throws SQLException;

    @Insert("insert into product(title,description,type,price,status,publisher_id,publish_time,update_time) values"+
            "(#{title},#{description},#{type},#{price},#{status},#{publisher_id},#{publish_time},#{update_time})")
    public int insert(Product product) throws SQLException;

    @Update("update product set title = #{title}, description = #{description}, type = #{type}, price = #{price}," +
            "status = #{status}, update_time = #{update_time} where id = #{id}")
    public int update(Product product) throws SQLException;

}
