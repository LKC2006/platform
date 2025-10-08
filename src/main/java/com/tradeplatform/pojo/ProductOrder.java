package com.tradeplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    private long id;
    private long product_id;
    private long buyer_id;
    private String status;
    private LocalDateTime create_time = LocalDateTime.now();
    private LocalDateTime update_time = LocalDateTime.now();

    //有了lombok不需要的getter setter 和 构造方法

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public long getProduct_id() {
//        return product_id;
//    }
//
//    public void setProduct_id(long product_id) {
//        this.product_id = product_id;
//    }
//
//    public long getBuyer_id() {
//        return buyer_id;
//    }
//
//    public void setBuyer_id(long buyer_id) {
//        this.buyer_id = buyer_id;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public LocalDateTime getCreate_time() {
//        return create_time;
//    }
//
//    public void setCreate_time(LocalDateTime create_time) {
//        this.create_time = create_time;
//    }
//
//    public LocalDateTime getUpdate_time() {
//        return update_time;
//    }
//
//    public void setUpdate_time(LocalDateTime update_time) {
//        this.update_time = update_time;
//    }
//
//    public ProductOrder(long id, long product_id, long buyer_id, String status, LocalDateTime create_time, LocalDateTime update_time) {
//        this.id = id;
//        this.product_id = product_id;
//        this.buyer_id = buyer_id;
//        this.status = status;
//        this.create_time = create_time;
//        this.update_time = update_time;
//    }
//
//    public ProductOrder() {
//    }
//    // to String()
//
//    @Override
//    public String toString() {
//        return "Product_Order{" +
//                "id=" + id +
//                ", product_id=" + product_id +
//                ", buyer_id=" + buyer_id +
//                ", status='" + status + '\'' +
//                ", create_time=" + create_time +
//                ", update_time=" + update_time +
//                '}';
//    }
}
