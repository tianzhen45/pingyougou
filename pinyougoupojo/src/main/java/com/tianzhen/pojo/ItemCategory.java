package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class ItemCategory implements Serializable{
    private Long id;

    private Long parentId;

    private String name;

    private Long typeId;

}