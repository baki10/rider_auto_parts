package com.bakigoal.spring.greeter;

/**
 * Created by ilmir on 04.10.16.
 */
public class RussianGreeter implements Greeter {
  public String sayHello() {
    return "Привет " + System.getProperty("user.name");
  }
}
