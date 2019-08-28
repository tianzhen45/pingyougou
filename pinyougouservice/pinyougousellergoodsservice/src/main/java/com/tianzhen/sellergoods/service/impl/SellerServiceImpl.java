package com.tianzhen.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.SellerMapper;
import com.tianzhen.pojo.Seller;
import com.tianzhen.service.SellerService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Date;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService{
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public void save(Seller seller) {
        seller.setCreateTime(new Date());
        seller.setPassword(new Md5Hash(seller.getName()).toString());
        sellerMapper.insertSelective(seller);
    }

    @Override
    public void update(Seller seller) {
        sellerMapper.updateByPrimaryKeySelective(seller);
    }

    @Override
    public void delete(String id) {
        sellerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Seller findById(String id) {
        return sellerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Seller> findAll() {
        return sellerMapper.findAll();
    }

    @Override
    public PageInfo<Seller> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Seller> sellerList = sellerMapper.findAll();
        return new PageInfo<>(sellerList);
    }

    @Override
    public Seller findByUsername(String username) {
        return sellerMapper.findByUsername(username);
    }
}
