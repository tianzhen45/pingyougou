package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.BrandMapper;
import com.tianzhen.pojo.Brand;
import com.tianzhen.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(interfaceName = "com.tianzhen.service.BrandService")
@Transactional
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> findAll() {

        return brandMapper.findAll();

    }

    @Override
    public void save(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public PageInfo<Brand> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Brand>(brandMapper.findAll());
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id.longValue());
    }

    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        return brandMapper.findAllByIdAndName();
    }


}
