package com.tradeplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String username;
    private String password;
    private String role;
    private String phone;
    LocalDateTime create_time=LocalDateTime.now();
    LocalDateTime update_time=LocalDateTime.now();

    //驼峰命名
//    public void setId(int id){
//        this .id = id;
//    }
//    public void setUsername(String username){
//        this.username = username;
//    }
//    public void serPassword(String password){
//        this.password = password;
//    }
//    public void setRole(String role){
//        this.role = role;
//    }
//    public void setPhone(String phone){
//        this.phone = phone;
//    }
//    public void setCreateTime(LocalDateTime create_time){
//        this.create_time = create_time;
//    }
//    public void setUpdateTime(LocalDateTime update_time){
//        this.update_time = update_time;
//    }
//    public long getId(){
//        return id;
//    }
//    public String getUsername(){
//        return username;
//    }
//    public String getPassword(){
//        return password;
//    }
//    public String getRole(){
//        return role;
//    }
//    public String getPhone(){
//        return phone;
//    }
//    public LocalDateTime getCreateTime(){
//        return create_time;
//    }
//    public LocalDateTime getUpdateTime(){
//        return update_time;
//    }
//    public User(long id, String username, String password, String role, String phone, LocalDateTime create_time, LocalDateTime update_time){
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.role = role;
//        this.phone = phone;
//        this.create_time = create_time;
//        this.update_time = update_time;
//    }
//    public User(){
//    }
//    //to String()用 System.out.println(对象) 打印对象时；
//    //用字符串拼接（+）连接对象时；
//    //日志框架（如 log.info(对象)）记录对象时
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", role='" + role + '\'' +
//                ", phone='" + phone + '\'' +
//                ", create_time=" + create_time +
//                ", update_time=" + update_time +
//                '}';
//    }
}
