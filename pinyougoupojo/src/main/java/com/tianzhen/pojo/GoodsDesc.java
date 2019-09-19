package com.tianzhen.pojo;

import lombok.*;

import java.io.Serializable;

@Data
public class GoodsDesc implements Serializable{
    private Long goodsId;

    private String introduction;

    private String specificationItems;

    private String customAttributeItems;

    private String itemImages;

    private String packageList;

    private String saleService;

}