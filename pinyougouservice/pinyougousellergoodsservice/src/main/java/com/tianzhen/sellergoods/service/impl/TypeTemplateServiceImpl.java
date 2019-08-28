package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.SpecificationOptionMapper;
import com.tianzhen.mapper.TypeTemplateMapper;
import com.tianzhen.mapper.TypeTemplateMapper;
import com.tianzhen.pojo.SpecificationOption;
import com.tianzhen.pojo.TypeTemplate;
import com.tianzhen.pojo.TypeTemplate;
import com.tianzhen.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService{

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public List<TypeTemplate> findAll() {

        return typeTemplateMapper.findAll();

    }

    @Override
    public void save(TypeTemplate typeTemplate) {
        typeTemplateMapper.insertSelective(typeTemplate);
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
    }

    @Override
    public PageInfo<TypeTemplate> findByPage(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<TypeTemplate>(typeTemplateMapper.findAll());
    }

    @Override
    public void delete(Integer id) {
        typeTemplateMapper.deleteByPrimaryKey(id.longValue());
    }

    @Override
    public List<Map<String,Object>> findByIdAndName() {
        return typeTemplateMapper.findByIdAndName();
    }

    @Override
    public TypeTemplate findById(String id) {
        return typeTemplateMapper.selectByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public List<Map> findSpecByTemplateId(Long id) {
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        String specIds = typeTemplate.getSpecIds();
        List<Map> specList = JSON.parseArray(specIds,Map.class);

        for (Map map : specList) {
            List<SpecificationOption> specOptions = specificationOptionMapper.findBySpecId(Integer.valueOf(map.get("id").toString()));
            map.put("options",specOptions);
        }

        return specList;
    }

}
