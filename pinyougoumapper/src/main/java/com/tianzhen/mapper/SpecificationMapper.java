package com.tianzhen.mapper;

import com.tianzhen.pojo.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Specification record);

    int insertSelective(Specification record);

    Specification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Specification record);

    int updateByPrimaryKey(Specification record);

    List<Specification> findAll();

    List<Map<String,Object>> findAllByIdAndName();
}