package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    List<TypeTemplate> findAll();

    void save(TypeTemplate typeTemplate);

    void update(TypeTemplate typeTemplate);

    PageInfo<TypeTemplate> findByPage(int pageNum, int pageSize);

    void delete(Integer id);

    List<Map<String,Object>> findByIdAndName();

    TypeTemplate findById(String id);

    List<Map> findSpecByTemplateId(Long id);

}
