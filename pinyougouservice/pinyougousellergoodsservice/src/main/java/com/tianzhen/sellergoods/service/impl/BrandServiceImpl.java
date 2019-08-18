package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianzhen.mapper.BrandMapper;
import com.tianzhen.pojo.Brand;
import com.tianzhen.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceName = "com.tianzhen.service.BrandService")
@Transactional
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
