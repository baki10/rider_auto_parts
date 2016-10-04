package com.bakigoal.spring;

import com.bakigoal.spring.greeter.Greeter;

/**
 * Created by ilmir on 04.10.16.
 */
public class GreetMeBean {
  private Greeter greeter;

  public void setGreeter(Greeter greeter) {
    this.greeter = greeter;
  }

  public void greet(){
    System.out.println(greeter.sayHello());
  }
}
