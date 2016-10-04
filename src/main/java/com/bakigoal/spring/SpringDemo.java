package com.bakigoal.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ilmir on 04.10.16.
 */
public class SpringDemo {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    GreetMeBean greetMeBean = (GreetMeBean) context.getBean("greetMeBean");
    greetMeBean.greet();
  }
}
