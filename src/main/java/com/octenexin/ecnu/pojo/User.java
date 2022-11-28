package com.octenexin.ecnu.pojo;


import lombok.Data;

@Data
public class User {
    private String email;   //primary key
    private String userId;
    private String userName;
    private String userPassword;
    private String major;
    private Integer authority;

}
