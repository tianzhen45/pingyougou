package com.tianzhen.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tianzhen.mapper.UserMapper;
import com.tianzhen.pojo.User;
import com.tianzhen.service.UserService;
import com.tianzhen.utils.HttpClientUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Value("${signName}")
    private String signName;

    @Value("${templateCode}")
    private String tempalteCode;

    @Value("${smsUrl}")
    private String smsUrl;


    @Override
    public void save(User user) {


        // 创建日期
        user.setCreated(new Date());
        // 修改日期
        user.setUpdated(user.getCreated());
        // 密码加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userMapper.insertSelective(user);

    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }


    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageInfo<User> findByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<User>(userMapper.findAll());
    }

    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> findAllByIdAndName() {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public boolean sendCode(String phone) {
        //生成6位随机验证码
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code += random.nextInt(10) +"";
        }

        System.out.println(code);

        //用httpclient调用短信发送接口
        HttpClientUtils httpClientUtils = new HttpClientUtils(false);
        //创建Map集合封装请求参数
        Map<String,String> param = new HashMap<>();
        param.put("phone",phone);
        param.put("signName",signName);
        param.put("templateCode",tempalteCode);
        param.put("templateParam","{'code': '"+code+"'}");
        //发送post请求
        String content= httpClientUtils.sendPost(smsUrl, param);
        Map<String,Object> map = JSON.parseObject(content, Map.class);

        //将验证码存入redis中，90s
        redisTemplate.boundValueOps(phone).set(code,180, TimeUnit.SECONDS);

        return (boolean)map.get("success");
    }

    @Override
    public boolean checkSmsCode(String phone, String smsCode) {
        //获取redis中的验证码
        String sysCode = redisTemplate.boundValueOps(phone).get();
        return StringUtils.isNoneBlank(sysCode) && sysCode.equals(smsCode);
    }
}
