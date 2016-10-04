package com.bakigoal.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ilmir on 04.10.16.
 */
public class SpringDemo {
  public static void main(String[] args) throws InterruptedException {
    ApplicationContext context = new ClassPathXmlApplicationContext("camel-beans.xml", "beans.xml");
    GreetMeBean greetMeBean = (GreetMeBean) context.getBean("greetMeBean");
    greetMeBean.greet();
    Thread.sleep(10000);
  }
}
