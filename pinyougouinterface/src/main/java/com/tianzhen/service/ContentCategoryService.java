package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.ContentCategory;

import java.util.List;

public interface ContentCategoryService {
    PageInfo<ContentCategory> findByPage(ContentCategory contentCategory, int page, int pageSize);

    List<ContentCategory> findAll();
}
