package com.tianzhen.pojo;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable{
    private Long id;

    private String specName;

    private List<SpecificationOption> specificationOptions;

    public List<SpecificationOption> getSpecificationOptions() {
        return specificationOptions;
    }

    public void setSpecificationOptions(List<SpecificationOption> specificationOptions) {
        this.specificationOptions = specificationOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }


}