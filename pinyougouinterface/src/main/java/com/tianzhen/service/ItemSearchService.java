package com.tianzhen.service;

import java.util.Map;

public interface ItemSearchService {

    Map<String,Object> search(Map<String,Object> params);

    Map<String,Object> searchHighlight(Map<String,Object> params);
}
