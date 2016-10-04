package com.bakigoal.spring.greeter;

/**
 * Created by ilmir on 04.10.16.
 */
public class EnglishGreeter implements Greeter {
  public String sayHello() {
    return "Hello " + System.getProperty("user.name");
  }
}
