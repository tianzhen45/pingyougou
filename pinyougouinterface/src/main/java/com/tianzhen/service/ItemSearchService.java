package com.tianzhen.service;

;
import com.tianzhen.pojo.EsItem;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

    Map<String,Object> search(Map<String,Object> params);

    Map<String,Object> searchHighlight(Map<String,Object> params);

    void saveOrUpdate(List<EsItem> esItems);

    void delete(List<Long> ids);
}
