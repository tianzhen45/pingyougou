package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
public class Specification implements Serializable{
    private Long id;

    private String specName;

    private List<SpecificationOption> specificationOptions;


}