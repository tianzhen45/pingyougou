package com.tianzhen.service;

import com.tianzhen.pojo.ItemCategory;

import java.util.List;

public interface ItemCategoryService {

    List<ItemCategory> findByParentId(Integer parentId);

    void save(ItemCategory itemCategory);

    void update(ItemCategory itemCategory);

    void delete(Integer id);

    boolean deleteByArr(Integer[] ids);
}
