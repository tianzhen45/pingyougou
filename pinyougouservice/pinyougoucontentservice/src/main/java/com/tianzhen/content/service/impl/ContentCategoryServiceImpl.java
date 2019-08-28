package com.tianzhen.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.ContentCategoryMapper;
import com.tianzhen.pojo.ContentCategory;
import com.tianzhen.pojo.ContentCategoryExample;
import com.tianzhen.service.ContentCategoryService;
import com.tianzhen.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

    @Autowired
    private ContentCategoryMapper contentCategoryMapper;


    @Autowired
    private ContentCategoryMapper contentCategoryMapperMapper;

    @Override
    public PageInfo<ContentCategory> findByPage(ContentCategory contentCategory, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        ContentCategoryExample contentCategoryExample = new ContentCategoryExample();
        ContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();


        List<ContentCategory> contentCategorys = contentCategoryMapper.selectByExample(contentCategoryExample);
        return new PageInfo<>(contentCategorys);
    }

    @Override
    public List<ContentCategory> findAll() {
        return contentCategoryMapper.selectByExample(new ContentCategoryExample());
    }

}
