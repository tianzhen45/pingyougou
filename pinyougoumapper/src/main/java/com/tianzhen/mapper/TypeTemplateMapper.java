package com.tianzhen.mapper;

import com.tianzhen.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TypeTemplate record);

    int insertSelective(TypeTemplate record);

    TypeTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TypeTemplate record);

    int updateByPrimaryKey(TypeTemplate record);

    List<TypeTemplate> findAll();

    List<Map<String,Object>> findByIdAndName();
}