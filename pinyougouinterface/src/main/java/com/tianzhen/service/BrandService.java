package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Brand;

import java.util.List;

public interface BrandService {

    List<Brand> findAll();

    void save(Brand brand);

    void update(Brand brand);

    PageInfo<Brand> findByPage(int pageNum,int pageSize);

    void delete(Integer id);
}
