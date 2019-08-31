package com.tianzhen.search.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class EsItem implements Serializable {

    // SKU商品id

    private Long id;

    // SKU商品标题

    private String title;


    // SKU商品价格

    private Double price;

    // SKU商品图片

    private String image;

    // SKU商品关联的SPU的id

    private Long goodsId;

    // SKU商品关联的商品分类

    private String category;


    // SKU商品关联的品牌

    private String brand;

    // SKU商品关联的商家

    private String seller;

    // SKU商品更新时间

    private Date updateTime;

    // SKU商品规格选项(嵌套类型，多种规格)

    private Map<String,String> spec;
    public Map<String, String> getSpec() {
        return spec;
    }
    public void setSpec(Map<String, String> spec) {
        this.spec = spec;
    }


    /** setter and getter method */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}