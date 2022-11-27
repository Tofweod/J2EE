package com.octenexin.ecnu.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Message {
    private Integer messageId;
    private String messageUserId;
    private String messageRawData;
    private Timestamp messageTime;
    private Integer messageHasread;


}
