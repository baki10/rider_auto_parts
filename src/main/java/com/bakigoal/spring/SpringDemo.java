package com.bakigoal.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ilmir on 04.10.16.
 */
public class SpringDemo {
  public static void main(String[] args) throws InterruptedException {
    new ClassPathXmlApplicationContext("camel-beans.xml");
  }
}
