package com.tianzhen.es.dao;

import com.tianzhen.pojo.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface EsItemDao extends ElasticsearchRepository<EsItem,Long>{
}
