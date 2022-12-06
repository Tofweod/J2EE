package com.octenexin.ecnu.pojo;

import lombok.Data;

@Data
public class Paper {
    private Integer paperId;
    private String paperTitle;
    private String paperAuthor;
    private String paperSummary;
    private String paperKeywords;
    private Integer paperStateId;
    private Integer paperPrestateId;
    private String paperUrl;
}
