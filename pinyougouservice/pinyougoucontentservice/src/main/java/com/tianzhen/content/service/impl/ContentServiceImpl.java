package com.tianzhen.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.ContentMapper;
import com.tianzhen.pojo.Content;
import com.tianzhen.pojo.ContentExample;
import com.tianzhen.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;

    @Override
    public PageInfo<Content> findByPage(Content content, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();


        List<Content> contents = contentMapper.selectByExample(contentExample);
        return new PageInfo<>(contents);
    }

    @Override
    public void save(Content content) {
        contentMapper.insertSelective(content);
    }

    @Override
    public void update(Content content) {
        contentMapper.updateByPrimaryKeySelective(content);
    }

    @Override
    public void delete(String id) {
        contentMapper.deleteByPrimaryKey(Long.valueOf(id));
    }
}
