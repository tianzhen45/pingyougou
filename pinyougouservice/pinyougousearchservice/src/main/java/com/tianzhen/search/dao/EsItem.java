package com.tianzhen.search.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Document(indexName = "pinyougou",type = "item")
public class EsItem implements Serializable {

    // SKU商品id
    @Id
    @Field(store = true,index = true,type = FieldType.Long)
    private Long id;

    // SKU商品标题
    @Field(store = true,
            index = true,
            type = FieldType.Text,
            analyzer = "ik_smart",
            searchAnalyzer = "ik_smart",
            copyTo = "keywords"
    )
    private String title;


    // SKU商品价格
    @Field(store = true,index = true,type = FieldType.Double)
    private Double price;

    // SKU商品图片
    @Field(store = true,index = false,type = FieldType.Text)
    private String image;

    // SKU商品关联的SPU的id
    @Field(store = true,type = FieldType.Long)
    private Long goodsId;

    // SKU商品关联的商品分类
    @Field(store = true,type = FieldType.Keyword,copyTo = "keywords")
    private String category;


    // SKU商品关联的品牌
    @Field(store = true,type = FieldType.Keyword,copyTo = "keywords")
    private String brand;

    // SKU商品关联的商家
    @Field(store = true,type = FieldType.Keyword,copyTo = "keywords")
    private String seller;

    // SKU商品更新时间
    @Field(store = true, type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // SKU商品规格选项(嵌套类型，多种规格)
    @Field(store = true, type = FieldType.Nested)
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