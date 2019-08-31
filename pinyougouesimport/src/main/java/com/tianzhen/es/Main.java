package com.tianzhen.es;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext-*.xml");
        ItemImport itemImport = context.getBean(ItemImport.class);
        itemImport.importData();
    }
}
