package com.tradeplatform.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {//实体类
    private long id;
    private String title;
    private String description;
    private String type;
    private BigDecimal price;
    private String status;
    private long publisher_id;
    private LocalDateTime publish_time = LocalDateTime.now();
    private LocalDateTime update_time  = LocalDateTime.now();

//    public Product(long id, String title, String description, String type, BigDecimal price, String status, long publisher_id, LocalDateTime publish_time, LocalDateTime update_time) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.type = type;
//        this.price = price;
//        this.status = status;
//        this.publisher_id = publisher_id;
//        this.publish_time = publish_time;
//        this.update_time = update_time;
//    }
//    public Product() {}
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
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
//    public long getPublisher_id() {
//        return publisher_id;
//    }
//
//    public void setPublisher_id(long publisher_id) {
//        this.publisher_id = publisher_id;
//    }
//
//    public LocalDateTime getPublish_time() {
//        return publish_time;
//    }
//
//    public void setPublish_time(LocalDateTime publish_time) {
//        this.publish_time = publish_time;
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
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", type='" + type + '\'' +
//                ", price=" + price +
//                ", status='" + status + '\'' +
//                ", publisher_id=" + publisher_id +
//                ", publish_time=" + publish_time +
//                ", update_time=" + update_time +
//                '}';
//    }
}
