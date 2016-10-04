package com.bakigoal.spring.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by ilmir on 04.10.16.
 */
@Component
public class JmsInTestRoute extends SpringRouteBuilder {

  private static final String JMS_URI = "jms:activemqTest";

  public void configure() throws Exception {

    from(JMS_URI)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Receiving: ");
            System.out.println(exchange.getIn().getBody());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
          }
        });
  }
}
