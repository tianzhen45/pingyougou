package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class Content implements Serializable{
    private Long id;

    private Long categoryId;

    private String title;

    private String url;

    private String pic;

    private String status;

    private Integer sortOrder;

}