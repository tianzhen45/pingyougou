package com.tianzhen.service;

public interface SmsService {
    /**
     * 发送短信的方法
     */
     boolean sendSms(String phone,String signName,String templateCode,String templateParam);
}
