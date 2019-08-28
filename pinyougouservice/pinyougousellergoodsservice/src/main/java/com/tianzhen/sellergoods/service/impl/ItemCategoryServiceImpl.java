package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tianzhen.mapper.ItemCategoryMapper;
import com.tianzhen.pojo.ItemCategory;
import com.tianzhen.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService{
    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Override
    public List<ItemCategory> findByParentId(Integer parentId) {
        return itemCategoryMapper.findByParentId(parentId);
    }

    @Override
    public void save(ItemCategory itemCategory) {
        itemCategoryMapper.insertSelective(itemCategory);
    }

    @Override
    public void update(ItemCategory itemCategory) {
        itemCategoryMapper.updateByPrimaryKeySelective(itemCategory);
    }

    @Override
    public void delete(Integer id) {
        itemCategoryMapper.deleteByPrimaryKey(Long.valueOf(id));
    }

    @Override
    public boolean deleteByArr(Integer[] ids) {
        for (Integer id : ids) {
            if(itemCategoryMapper.findByParentId(id).size()>0){
                return false;
            }
        }
        for (Integer id : ids) {
            itemCategoryMapper.deleteByPrimaryKey(Long.valueOf(id));
        }
        return true;
    }
}
