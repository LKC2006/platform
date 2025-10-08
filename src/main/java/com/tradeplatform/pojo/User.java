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

}
