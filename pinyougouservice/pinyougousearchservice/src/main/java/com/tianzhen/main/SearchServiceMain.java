package com.tianzhen.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class SearchServiceMain {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-*.xml");
        applicationContext.start();
        System.in.read();
    }
}
