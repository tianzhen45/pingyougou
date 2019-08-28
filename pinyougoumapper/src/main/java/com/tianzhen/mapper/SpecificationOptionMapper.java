package com.tianzhen.mapper;

import com.tianzhen.pojo.Specification;
import com.tianzhen.pojo.SpecificationOption;

import java.util.List;

public interface SpecificationOptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpecificationOption record);

    int insertSelective(SpecificationOption record);

    SpecificationOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpecificationOption record);

    int updateByPrimaryKey(SpecificationOption record);

    void save(Specification specification);

    void deleteBySpecId(Specification specification);

    List<SpecificationOption> findBySpecId(Integer id);

}