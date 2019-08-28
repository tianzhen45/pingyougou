package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Brand;
import com.tianzhen.pojo.Specification;
import com.tianzhen.pojo.SpecificationOption;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    List<Specification> findAll();

    void save(Specification brand);

    void update(Specification brand);

    PageInfo<Specification> findByPage(int pageNum, int pageSize);

    void delete(Integer id);

    List<SpecificationOption> findSpecificationOptions(Integer id);

    List<Map<String,Object>> findAllByIdAndName();
}
