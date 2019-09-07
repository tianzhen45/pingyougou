package com.tianzhen.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Goods;
import com.tianzhen.service.GoodsService;
import com.tianzhen.shop.util.UploadUtils;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private MQProducer mqProducer;


    @PostMapping("/save")
    public Boolean save(@RequestBody Goods goods){
        try{
            goodsService.save(goods);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("file") MultipartFile file){
        Map<String,Object> data = new HashMap<>();
        data.put("status","500");
        try{
            String url = uploadUtils.upload(file);
            data.put("url","http://"+url);
        }catch (Exception e){
            e.printStackTrace();
        }
        data.put("status","200");
        return data;
    }

    @GetMapping("/findByPage")
    public PageInfo<Goods> findByPage(Goods goods,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize){
        PageInfo<Goods> pageInfo = goodsService.findByPage(goods,page, pageSize);
        return pageInfo;
    }


    //商品商家上架
    @GetMapping("/updateMarketable")
    public boolean updateMarketable(String[] ids,String marketable){
        try{
            if(ids != null && ids.length  > 0){
                for (String id : ids) {
                    Goods goods = new Goods();
                    goods.setId(Long.valueOf(id));
                    goods.setIsMarketable(marketable);
                    goodsService.update(goods);
                }
                //判断是商品上架还是下架
                if("1".equals(marketable)){
                    //是商品上架，发送消息给搜索系统，更新索引
                    mqProducer.send(new Message(
                            "ES_ITEM_TOPIC","UPDATE", JSON.toJSONString(ids).getBytes("UTF-8")
                    ));

                    //商品上架，发消息给商品详情系统，生成详情网页
                    mqProducer.send(new Message("PAGE_ITEM_TOPIC","CREATE",JSON.toJSONString(ids).getBytes("UTF-8")));


                }else {
                    //商品下架,发送消息给搜索系统，删除索引
                    mqProducer.send(new Message(
                            "ES_ITEM_TOPIC","DELETE", JSON.toJSONString(ids).getBytes("UTF-8")
                    ));

                    //商品下架，发消息给商品详情系统，删除详情网页
                    mqProducer.send(new Message("PAGE_ITEM_TOPIC","DELETE",JSON.toJSONString(ids).getBytes("UTF-8")));
                }

                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @GetMapping("/delete")
    public boolean delete(String[] ids){
        try{
            if(ids != null && ids.length > 0){
                for (String id : ids) {
                    goodsService.deleteLogic(id);
                }
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
