package com.tianzhen.item.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tianzhen.pojo.Goods;
import com.tianzhen.service.GoodsService;
import freemarker.template.Template;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

@Component
public class PageMessageListener implements MessageListenerConcurrently{

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Reference(timeout = 30000)
    private GoodsService goodsService;
    @Value("${page.dir}")
    private String pageDir;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try{
            MessageExt messageExt = list.get(0);
            String tags = messageExt.getTags();
            String topic = messageExt.getTopic();
            byte[] body = messageExt.getBody();
            String content = new String(body, "UTF-8");
            //把json字符串转换
            List<Long> ids = JSON.parseArray(content, Long.class);

            System.out.println("tags: ===  "+tags );
            System.out.println("topic: ===  "+topic );
            if(ids != null & ids.size() >0) {
                if ("CREATE".equals(tags)) {

                    //判断网页存储目录
                    File file = new File(pageDir);
                    if(!file.exists()) file.mkdir();

                    //使用freemarker创建商品详情静态网页
                    //1.获取模板对象
                    Template template = freeMarkerConfigurer.getConfiguration().getTemplate("item.ftl");
                    //2.循环生成静态页面
                    for (Long id : ids) {
                        //得到数据模型包括商品，商品详情，SKU列表
                        Map<String, Object> dataModel = goodsService.getGoods(id);

                        //创建输出流
                        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(pageDir + id + ".html"), "UTF-8");
                        //填充模板，生成文件
                        template.process(dataModel, out);
                        //关闭输出流
                        out.close();
                    }
                }
                //删除商品详静态网页
                if ("DELETE".equals(tags)) {
                    for (Long id : ids) {
                        File file = new File(pageDir + id + ".html");
                        if(file.exists()){
                            file.delete();
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
