package com.tianzhen.mapper;

import com.tianzhen.pojo.Brand;

import java.util.List;
import java.util.Map;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKey(Brand record);

    List<Brand> findAll();

    List<Map<String,Object>> findAllByIdAndName();
}