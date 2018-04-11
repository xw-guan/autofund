package com.xwguan.autofund.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocUtils {

    private static ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-*.xml");

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
    
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
    
    public static <T> T getBean(Class<T> beanClass, String beanName) {
        return context.getBean(beanClass, beanName);
    }
} 
