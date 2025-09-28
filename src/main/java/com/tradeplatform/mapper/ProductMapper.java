package com.tradeplatform.mapper;

import com.tradeplatform.pojo.Product;
import com.tradeplatform.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//仅负责数据访问,接口实现功能
@Mapper//数据访问层（dao）,自动创建接口的代理对象
public interface ProductMapper {//自动生成实现类对象ProductMapper.list()

    @Select("select * from product")//注解中指定要执行的SQL语句
    public List<Product> list();//结果封装到方法的返回值中，mybatis自动完成

    //商品详情
    @Select("select * from product where title = #{title}")
    public List<Product> selectByTitle(String title);

    //查询商品列表
    @Select("select title from product")
    public List<String> getTitle();

    @Delete("delete from product where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    @Insert("insert into product(title,description,type,price,status,publisher_id,publish_time,update_time) values"+
            "(#{title},#{description},#{type},#{price},#{status},#{publisher_id},#{publish_time},#{update_time})")
    public void insert(Product product);

    @Update("update product set title = #{title}, description = #{description}, type = #{type}, price = #{price}," +
            "status = #{status}, update_time = #{update_time} where id = #{id}")
    public void update(Product product);

}
