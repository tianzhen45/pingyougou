package com.tianzhen.service;

import com.github.pagehelper.PageInfo;
import com.tianzhen.pojo.Goods;

public interface GoodsService {

    void save(Goods goods);

    PageInfo<Goods> findByPage(Goods goods,int page, int pageSize);

    void update(Goods goods);

    void deleteLogic(String id);
}
