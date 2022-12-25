package com.octenexin.ecnu.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectDelay {

    private Integer projectDelayId;
    private Integer projectId;
    private Date projectOldEndTime;
    private Date projectNewEndTime;
    private String projectDelayReason;
    private Integer projectDelayState;

}
