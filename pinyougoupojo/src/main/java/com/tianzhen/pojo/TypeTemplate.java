package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class TypeTemplate implements Serializable {
    private Long id;

    private String name;

    private String specIds;

    private String brandIds;

    private String customAttributeItems;

}