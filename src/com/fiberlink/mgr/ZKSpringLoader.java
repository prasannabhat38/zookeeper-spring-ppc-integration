package com.fiberlink.mgr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ZKSpringLoader {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
                                                "classpath:com/fiberlink/mgr/zkclient-beans.xml"});
        SampleService svc = (SampleService) context.getBean("sampleService");
        
        System.out.println("Property1 Value: " + svc.getProperty1());
        System.out.println("Property2 Value: " + svc.getProperty2());
        
        System.out.println("Jdbc Url: " + svc.getJdbcUrl());
        System.out.println("Jdbc Password: " + svc.getJdbcPasswd());
    }
}
