package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class SpecificationOption implements Serializable{
    private Long id;

    private String optionName;

    private Long specId;

    private Integer orders;

}