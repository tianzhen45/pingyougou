package com.tianzhen.pojo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
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


}