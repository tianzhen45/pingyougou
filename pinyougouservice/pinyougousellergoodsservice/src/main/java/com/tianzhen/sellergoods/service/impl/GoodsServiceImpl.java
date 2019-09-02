package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.*;
import com.tianzhen.pojo.*;
import com.tianzhen.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void save(Goods goods) {
        //设置商品未审核状态
        goods.setAuditStatus("0");
        goodsMapper.insertSelective(goods);

        //设置商品描述的主键
        goods.getGoodsDesc().setGoodsId(goods.getId());
        goodsDescMapper.insertSelective(goods.getGoodsDesc());

        /** 判断是否启用规格 */
        if ("1".equals(goods.getIsEnableSpec())) {
            /** 迭代所有的SKU具体商品集合，往SKU表插入数据 */
            for (Item item : goods.getItems()) {
                /** 定义具体商品的标题 */
                StringBuilder title = new StringBuilder();
                title.append(goods.getGoodsName());
                /** 把规格选项JSON字符串转化成Map集合 */
                Map<String, Object> spec = JSON.parseObject(item.getSpec());
                for (Object value : spec.values()) {
                    /** 拼接规格选项到具体商品标题 */
                    title.append(" " + value);
                }
                /** 设置SKU商品的标题 */
                item.setTitle(title.toString());
                /** 设置SKU商品其它属性 */
                setItemInfo(item, goods);
                itemMapper.insertSelective(item);
            }
        } else {
            /** 创建SKU具体商品对象 */
            Item item = new Item();
            /** 设置SKU商品的标题 */
            item.setTitle(goods.getGoodsName());
            /** 设置SKU商品的价格 */
            item.setPrice(goods.getPrice());
            /** 设置SKU商品库存数据 */
            item.setNum(9999);
            /** 设置SKU商品启用状态 */
            item.setStatus("1");
            /** 设置是否默认*/
            item.setIsDefault("1");
            /** 设置规格选项 */
            item.setSpec("{}");
            /** 设置SKU商品其它属性 */
            setItemInfo(item, goods);
            itemMapper.insertSelective(item);
        }
    }

    /**
     * 设置SKU商品信息
     */
    private void setItemInfo(Item item, Goods goods) {
        /** 设置SKU商品图片地址 */
        List<Map> imageList = JSON.parseArray(
                goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList != null && imageList.size() > 0) {
            /** 取第一张图片 */
            item.setImage((String) imageList.get(0).get("url"));
        }
        /** 设置SKU商品的分类(三级分类) */
        item.setCategoryid(goods.getCategory3Id());
        /** 设置SKU商品的创建时间 */
        item.setCreateTime(new Date());
        /** 设置SKU商品的修改时间 */
        item.setUpdateTime(item.getCreateTime());
        /** 设置SPU商品的编号 */
        item.setGoodsId(goods.getId());
        /** 设置商家编号 */
        item.setSellerId(goods.getSellerId());
        /** 设置分类名称 */
        item.setCategory(itemCategoryMapper
                .selectByPrimaryKey(goods.getCategory3Id()).getName());
        /** 设置品牌名称 */
        item.setBrand(brandMapper
                .selectByPrimaryKey(goods.getBrandId()).getName());
        /** 设置商家店铺名称 */
        item.setSeller(sellerMapper.selectByPrimaryKey(
                goods.getSellerId()).getNickName());
    }


    @Override
    public PageInfo<Goods> findByPage(Goods goods, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();

        if (!StringUtils.isEmpty(goods.getAuditStatus())) {
            criteria.andAuditStatusEqualTo(goods.getAuditStatus());
        }
        if (!StringUtils.isEmpty(goods.getGoodsName())) {
            criteria.andGoodsNameEqualTo(goods.getGoodsName());
        }

        //排除逻辑删除的商品
        criteria.andIsDeleteIsNull();

        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        return new PageInfo<>(goodsList);
    }

    public void update(Goods goods) {
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public void deleteLogic(String id) {
        goodsMapper.deleteLogic(id);
    }

    @Override
    public Goods findById(Long goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public Map<String, Object> getGoods(Long goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
        Map<String,Object> data = new HashMap<>();

        //商品分类
        if(goods != null && goods.getCategory1Id() != null){
            String itemCat1 = itemCategoryMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            data.put("itemCat1",itemCat1);
        }

        if(goods != null && goods.getCategory2Id() != null){
            String itemCat2 = itemCategoryMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            data.put("itemCat2",itemCat2);
        }
        if(goods != null && goods.getCategory3Id() != null){
            String itemCat3 = itemCategoryMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            data.put("itemCat3",itemCat3);
        }


        data.put("goods",goods);
        data.put("goodsDesc",goodsDesc);


        //查询SKU，根据goodsId查询tb_item,返回itemList
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        //状态码为1
        criteria.andStatusEqualTo("1");
        //商品Id
        criteria.andGoodsIdEqualTo(goodsId);
        //按默认降序
        List<Item> itemList = itemMapper.selectByExample(itemExample);
        //转成JSON字符串
        data.put("itemList",JSON.toJSONString(itemList));
        return data;
    }

}
