package com.octenexin.ecnu.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class Project {

    private Integer projectId;
    private String projectName;
    private String projectChargePersonId;
    private String projectOtherPeopleInfo;
    private Integer projectFundsUp;
    private String projectAbout;
    private Integer projectPaperId;
    private Integer projectClassId;
    private Integer projectStateId;
    private Integer projectPrestateId;
    private Date projectStartTime;
    private Date projectEndTime;
}
