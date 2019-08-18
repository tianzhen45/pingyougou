package com.tianzhen.Main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SellerGoodsServiceMain {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-*.xml");
        applicationContext.start();
        System.in.read();
    }
}
