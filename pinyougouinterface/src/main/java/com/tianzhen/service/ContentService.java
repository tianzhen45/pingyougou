package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Content;

import java.util.List;

public interface ContentService {

    PageInfo<Content> findByPage(Content content,int page,int pageSize);

    void save(Content content);

    void update(Content content);

    void delete(String id);

    List<Content> findContentByCategoryId(String categoryId);
}
