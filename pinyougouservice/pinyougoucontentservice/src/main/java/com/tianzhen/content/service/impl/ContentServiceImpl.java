package com.tianzhen.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.ContentMapper;
import com.tianzhen.pojo.Content;
import com.tianzhen.pojo.ContentExample;
import com.tianzhen.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private RedisTemplate redisTemplate;

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
        try {
            contentMapper.insertSelective(content);

            redisTemplate.delete("content");
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void update(Content content) {
        try {
            contentMapper.updateByPrimaryKeySelective(content);
            redisTemplate.delete("content");
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void delete(String id) {
        try {
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
            redisTemplate.delete("content");
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }



    /** 根据分类id查询广告内容 */
    @Override
    public List<Content> findContentByCategoryId(String categoryId) {
        List<Content> contentList = null;
        try {
            contentList = (List<Content>) redisTemplate.boundValueOps("content").get();
            if(contentList != null && contentList.size() >0){
                return contentList;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(Long.valueOf(categoryId));
        criteria.andStatusEqualTo("1");
        contentList = contentMapper.selectByExample(contentExample);

        try{
            /** 存入Redis缓存 */
            redisTemplate.boundValueOps("content").set(contentList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return contentList;
    }
}
