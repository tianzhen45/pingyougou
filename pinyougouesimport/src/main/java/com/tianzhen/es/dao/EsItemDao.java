package com.tianzhen.es.dao;

import com.tianzhen.es.EsItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface EsItemDao extends ElasticsearchRepository<EsItem,Long>{
}
