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

}
