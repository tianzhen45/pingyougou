package com.tianzhen.mapper;

import com.tianzhen.pojo.Goods;
import com.tianzhen.pojo.GoodsExample;
import java.util.List;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    void deleteLogic(String id);
}