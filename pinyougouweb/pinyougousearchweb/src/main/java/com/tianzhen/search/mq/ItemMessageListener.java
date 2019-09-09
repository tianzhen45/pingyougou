package com.tianzhen.search.mq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tianzhen.pojo.EsItem;
import com.tianzhen.pojo.Item;
import com.tianzhen.service.GoodsService;
import com.tianzhen.service.ItemSearchService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemMessageListener implements MessageListenerConcurrently {

    @Reference(timeout = 30000)
    private GoodsService goodsService;

    @Reference(timeout = 30000)
    private ItemSearchService itemSearchService;


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        System.out.println("=====ItemMessageListener");
        try {
            //获取消息封装对象
            MessageExt messageExt = list.get(0);
            //获取消息内容
            byte[] body = messageExt.getBody();
            String content = new String(body, "UTF-8");
            //把JSON字符串转回ids
            List<Long> ids = JSON.parseArray(content, Long.class);
            System.out.println("ids:" + ids);
            System.out.println("tags:" + messageExt.getTags());

            if (ids != null && ids.size() > 0) {
                //判断消息标签操作
                if ("UPDATE".equals(messageExt.getTags())) {
                    //更新索引，实际上是更新索引库的SKU
                    //1.查询出商家上架商品的所有SKU,itemList
                    for (Long id : ids) {
                        List<Item> itemList = goodsService.findById(id).getItems();
                        ArrayList<EsItem> esItems = new ArrayList<>();
                        for (Item item : itemList) {
                            // 把List<Item>转化成List<EsItem>
                            EsItem esItem = new EsItem();

                            esItem.setId(item.getId());
                            esItem.setTitle(item.getTitle());
                            esItem.setPrice(item.getPrice().doubleValue());
                            esItem.setImage(item.getImage());
                            esItem.setGoodsId(item.getGoodsId());
                            esItem.setCategory(item.getCategory());
                            esItem.setBrand(item.getBrand());
                            esItem.setSeller(item.getSeller());
                            esItem.setUpdateTime(item.getUpdateTime());
                            // 把SKU的规格json字符串，转化成Map集合(fastjson)
                            Map<String, String> spec = JSON
                                    .parseObject(item.getSpec(), Map.class);
                            // 规格嵌套Field
                            esItem.setSpec(spec);

                            esItems.add(esItem);
                        }
                        //将SKU数据更新到索引库
                        itemSearchService.saveOrUpdate(esItems);


                    }
                } else if ("DELETE".equals(messageExt.getTags())) {
                    itemSearchService.delete(ids);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
