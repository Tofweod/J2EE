package com.octenexin.ecnu.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class ProjectType {
    private int projectTypeId;
    private int projectClassId;
    private String projectTypeName;
    private Date projectTypeStartTime;
    private Date projectTypeEndTime;
}
