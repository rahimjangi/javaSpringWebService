package com.raiseup.javaSpringWebService;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApplicationContext implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT=context;
        System.out.println("CONTEXT::::::::::::::::::"+CONTEXT);
    }

    public static <T> Object getBean(Class<T> tClass){
        return CONTEXT.getBean(tClass);
    }
}
