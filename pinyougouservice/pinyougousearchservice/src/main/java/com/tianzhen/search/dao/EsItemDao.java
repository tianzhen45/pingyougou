package com.tianzhen.search.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsItemDao extends ElasticsearchRepository<EsItem,Long>{
}
