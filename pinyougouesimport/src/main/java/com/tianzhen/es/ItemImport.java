package com.tianzhen.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tianzhen.es.dao.EsItemDao;
import com.tianzhen.mapper.ItemMapper;
import com.tianzhen.pojo.EsItem;
import com.tianzhen.pojo.Item;
import com.tianzhen.pojo.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ItemImport {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired(required = false)
    private EsItemDao esItemDao;



    /*导入数据*/
    public void importData() throws IOException {
        ItemExample itemExample = new ItemExample();
        itemExample.createCriteria().andStatusEqualTo("1");
        List<Item> itemList = itemMapper.selectByExample(itemExample);

        System.out.println("===开始===");

        List<EsItem> esItemList = new ArrayList<>();
        for (Item item : itemList) {
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

            //把SKU的规格json字符串，转成Map
            Map<String,String> map = new ObjectMapper().readValue(item.getSpec(), Map.class);

            // 规格嵌套Field
            esItem.setSpec(map);
            esItemList.add(esItem);
        }

        esItemDao.saveAll(esItemList);
        System.out.println("===结束===");
    }


}
