package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class Brand implements Serializable{
    private Long id;

    private String name;

    private String firstChar;

}