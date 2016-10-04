package com.bakigoal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by ilmir on 04.10.16.
 */
public class DownloadLoggerProcessor implements Processor {
  public void process(Exchange exchange) throws Exception {
    System.out.println("From: \t" + exchange.getIn());
    System.out.println("To: \t" + (exchange.getOut()));
    System.out.println("----------------------------------------");
  }
}
