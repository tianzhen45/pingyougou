package com.tianzhen.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.tianzhen.pojo.EsItem;
import com.tianzhen.search.dao.EsItemDao;
import com.tianzhen.service.ItemSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemSearchServiceImpl implements ItemSearchService{
    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Autowired(required = false)
    private EsItemDao esItemDao;



    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        /** 获取查询关键字 */
        String keywords = (String)params.get("keywords");

        // 创建原生的搜索条件构建对象
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        // 设置默认搜索全部
        builder.withQuery(QueryBuilders.matchAllQuery());

        /** 判断检索关键字是否为空 */
        if (StringUtils.isNoneBlank(keywords)) {
            // 设置根据条件匹配查询: keywords 复制Field
            builder.withQuery(QueryBuilders
                    .matchQuery("keywords", keywords));
        }

        // 构建搜索查询对象
        SearchQuery query = builder.build();

        // 分页查询，得到合计分页对象
        AggregatedPage<EsItem> page = esTemplate
                .queryForPage(query, EsItem.class);

        // 定义Map集合封装返回数据
        Map<String,Object> data = new HashMap<>();

        // 设置总记录数
        data.put("total", page.getTotalElements());

        // 设置分页结果
        data.put("rows", page.getContent());

        return data;
    }

    @Override
    public Map<String, Object> searchHighlight(Map<String, Object> params) {
        //获取查询关键字
        String keywords = (String)params.get("keywords");

        //创建原生搜索条件构建对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //设置默认搜索全部
        queryBuilder.withQuery(QueryBuilders.matchAllQuery());

        //判断搜索关键字是否为空
        if(!StringUtils.isEmpty(keywords)){
            //设置根据多条件匹配
            queryBuilder
                    .withQuery(QueryBuilders
                            .multiMatchQuery
                                    (keywords,"title","category","brand","seller"));
        }

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();


        //2.1 按商品分类过滤
        String category = (String)params.get("category");
        if(StringUtils.isNoneBlank(category)){
            boolQueryBuilder.must(QueryBuilders.termQuery("category",category));
        }

        // 2.2 按商品品牌过滤
        String brand = (String)params.get("brand");
        if (StringUtils.isNoneBlank(brand)){
            // 词条查询
            boolQueryBuilder.must(QueryBuilders.termQuery("brand", brand));
        }

        // 2.3 按商品规格过滤
        Map<String,String> specMap = (Map<String, String>)params.get("spec");
        if (specMap != null && specMap.size() > 0){
            //迭代map
            for (String key : specMap.keySet()) {

                String field = "spec."+key+".keyword";
                //嵌套查询
                boolQueryBuilder
                        .must(
                                QueryBuilders
                                        .nestedQuery("spec"
                                                ,QueryBuilders.termQuery(field,specMap.get(key))
                                                , ScoreMode.Max));
            }
        }

        // 2.4 按商品价格区间过滤
        String price = (String)params.get("price");
        if (StringUtils.isNoneBlank(price)) {
            // 得到价格区间数组
            String[] priceArr = price.split("-");
            // 范围查询构建对象
            RangeQueryBuilder rqBuilder = QueryBuilders.rangeQuery("price");
            // 如果价格结束为星号
            if ("*".equals(priceArr[1])){
                // 大于起始价格 3000-*
                rqBuilder.gt(priceArr[0]);
            }else{
                // 从起始价格 到 结束价格
                rqBuilder.from(priceArr[0]).to(priceArr[1]);
            }
            // 组合范围查询
            boolQueryBuilder.must(rqBuilder);
        }



        //创建高亮字段
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("title")          //设置需要高亮的字段
                .preTags("<font color='red'>") // 设置高亮前缀
                .postTags("</font>")            //设置高亮后缀
                .fragmentSize(50);              //设置文本截断

        //设置高亮字段对象
        queryBuilder.withHighlightFields(field);

        //原生搜索查询对象添加过滤条件()
        queryBuilder.withFilter(boolQueryBuilder);


        //构建查询搜索对象
        SearchQuery query = queryBuilder.build();


        //排序查询
        String sortField = (String)params.get("sortField");
        String sortValue = (String)params.get("sortValue");
        if(StringUtils.isNoneBlank(sortValue) && StringUtils.isNoneBlank(sortField)){
            //创建排序对象
            Sort sort = new Sort("ASC".equalsIgnoreCase(sortValue) ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
            query.addSort(sort);
        }


        //分页参数
        Integer pageNum = (Integer) params.get("pageNum");
        int pageSize = 20;

        if(pageNum == null) {
            pageNum = 1;
        }

        // 创建分页参数封装对象,0是第一页
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        // 设置分页
        query.setPageable(pageable);




        //分页查询，得到合计分页对象
        AggregatedPage<EsItem> page = esTemplate.queryForPage(query, EsItem.class, new SearchResultMapper() {
           //搜索结果映射
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                //定义List集合封装分页结果
                List<T> content = new ArrayList<>();

                //循环封装搜索结果
                for(SearchHit hit:searchResponse.getHits()){
                    //获取搜索的文档json字符串，转成esItem对象
                    EsItem esItem = JSON.parseObject(hit.getSourceAsString(), EsItem.class);
                    //获取标题高亮对象
                    HighlightField highlightField = hit.getHighlightFields().get("title");
                    //判断是否有标题高亮对象
                    if(highlightField != null){
                        //获取标题高亮内容字符串
                        String title = highlightField.getFragments()[0].toString();
                        //设置标题高亮内容
                        esItem.setTitle(title);
                    }
                    //添加到集合中
                    content.add((T)esItem);
                }

                return new AggregatedPageImpl<T>(content,pageable,searchResponse.getHits().getTotalHits());
            }
        });

        //定义Map集合封装返回数据
        Map<String,Object> data = new HashMap<>();

        //设置总记录数
        data.put("total",page.getTotalElements());
        //设置分页结果
        data.put("rows",page.getContent());
        //设置总页数
        data.put("totalPages",page.getTotalPages());

        return data;
    }

    @Override
    public void saveOrUpdate(List<EsItem> esItems) {
        esItemDao.saveAll(esItems);
    }

    @Override
    public void delete(List<Long> ids) {
        try{
            DeleteQuery deleteQuery = new DeleteQuery();
            deleteQuery.setIndex("pinyougou");
            deleteQuery.setType("item");
            deleteQuery.setQuery(QueryBuilders.termQuery("goodsId",ids));
            esTemplate.delete(deleteQuery);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
