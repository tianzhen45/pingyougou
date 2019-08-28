package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.SpecificationMapper;
import com.tianzhen.mapper.SpecificationOptionMapper;
import com.tianzhen.pojo.Specification;
import com.tianzhen.pojo.Specification;
import com.tianzhen.pojo.SpecificationOption;
import com.tianzhen.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(timeout = 50000)
@Transactional
public class SpecificationServiceImpl implements SpecificationService{
    @Autowired
    private SpecificationMapper specificationMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<Specification> findAll() {

        return specificationMapper.findAll();

    }

    @Override
    public void save(Specification specification) {

        //插入规格表,同时用generatorKey把id取回来
        specificationMapper.insertSelective(specification);

        //插入规格选项表
        specificationOptionMapper.save(specification);
    }

    @Override
    public void update(Specification specification) {

        //更新规格表
        specificationMapper.updateByPrimaryKeySelective(specification);

        //更新规格选项表
        //1. 删除这条规格id的规格选项表记录
        specificationOptionMapper.deleteBySpecId(specification);

        //2. 插入更新后记录
        specificationOptionMapper.save(specification);
    }

    @Override
    public PageInfo<Specification> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Specification>(specificationMapper.findAll());
    }

    @Override
    public void delete(Integer id) {
        specificationMapper.deleteByPrimaryKey(id.longValue());
    }

    @Override
    public List<SpecificationOption> findSpecificationOptions(Integer id) {
        return specificationOptionMapper.findBySpecId(id);
    }

    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        return specificationMapper.findAllByIdAndName();
    }

}
