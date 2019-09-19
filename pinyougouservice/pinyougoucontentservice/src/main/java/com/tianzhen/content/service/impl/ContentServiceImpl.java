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
            
            //更新后的分类广告列表缓存
            deleteContentListInRedisByCategoryId(content.getCategoryId()+"");
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void update(Content content) {
        try {
            Content oldContent = contentMapper.selectByPrimaryKey(content.getId());
            
            //如果删除了分类id,把之前分类广告列表缓存删除
            if(!oldContent.getCategoryId().equals(content.getCategoryId())){
                deleteContentListInRedisByCategoryId(oldContent.getCategoryId()+"");
            }
            //删除更新后的分类广告列表缓存
            deleteContentListInRedisByCategoryId(content.getCategoryId()+"");
            
            contentMapper.updateByPrimaryKeySelective(content);
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }

    @Override
    public void delete(String id) {
        try {
            contentMapper.deleteByPrimaryKey(Long.valueOf(id));
            
            //删除该分类广告的列表缓存
            deleteContentListInRedisByCategoryId(contentMapper.selectByPrimaryKey(Long.valueOf(id)).getCategoryId()+"");
        }catch (Exception ex){
            throw  new RuntimeException(ex);
        }
    }



    /** 根据分类id查询广告内容 */
    @Override
    public List<Content> findContentByCategoryId(String categoryId) {
        List<Content> contentList = null;
        try {
            contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
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
        contentExample.setOrderByClause("sort_order desc");
        contentList = contentMapper.selectByExample(contentExample);

        try{
            /** 存入Redis缓存 */
            redisTemplate.boundHashOps("content").put(categoryId,contentList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return contentList;
    }

    public void deleteContentListInRedisByCategoryId(String categoryId){
        redisTemplate.boundHashOps("content").delete(categoryId);
    }
}
