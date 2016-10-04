package com.bakigoal;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

/**
 * We didn’t do any conversion from the FTP file type to the JMS message type —
 * this was done automatically by Camel’s TypeConverter facility
 * <p>
 * Created by ilmir on 04.10.16.
 */
public class FtpToJmsExample {

  private static final String FTP_URI = "ftp://rider.com/orders?username=rider&password=secret";
  private static final String JMS_URI = "jms:queue:incomingOrders";

  public static void main(String[] args) throws Exception {

    CamelContext context = new DefaultCamelContext();

    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

    context.addRoutes(new RouteBuilder() {
      public void configure() {
        from(FTP_URI)
            .process(new Processor() {
              public void process(Exchange exchange) throws Exception {
                System.out.println("We just downloaded: " + exchange.getIn().getHeader("CamelFileName"));
              }
            })
            .to(JMS_URI);
      }
    });
    context.start();
    Thread.sleep(10000);
    context.stop();
  }
}
