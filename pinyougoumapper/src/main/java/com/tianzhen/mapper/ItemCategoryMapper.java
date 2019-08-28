package com.tianzhen.mapper;

import com.tianzhen.pojo.ItemCategory;

import java.util.List;

public interface ItemCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemCategory record);

    int insertSelective(ItemCategory record);

    ItemCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemCategory record);

    int updateByPrimaryKey(ItemCategory record);

    List<ItemCategory> findByParentId(Integer parentId);
}