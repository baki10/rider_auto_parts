package com.bakigoal.spring.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by ilmir on 04.10.16.
 */
@Component
public class FileToJmsAnnotationRoute extends SpringRouteBuilder {

  private static final String FILE_URI = "file:src/data?noop=true";
  private static final String JMS_XML_URI = "jms:xmlOrders";
  private static final String JMS_CSV_URI = "jms:csvOrders";
  private static final String JMS_BAD_ORDER_URI = "jms:badOrders";

  private final String FILE_NAME_HEADER = "CamelFileName";

  public void configure() throws Exception {

    from(FILE_URI)
        .choice()
        .when(header(FILE_NAME_HEADER).endsWith(".xml")).to(JMS_XML_URI)
        .when(header(FILE_NAME_HEADER).endsWith(".csv")).to(JMS_CSV_URI)
        .otherwise().to(JMS_BAD_ORDER_URI);

    from(JMS_XML_URI)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("-----  Received XML order: " + exchange.getIn().getHeader(FILE_NAME_HEADER) + " -----");
            byte[] body = (byte[]) exchange.getIn().getBody();
            System.out.println(new String(body, "UTF-8"));
            System.out.println("----------------------------------------------------------");
          }
        });

    from(JMS_CSV_URI)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("Received CSV order: " + exchange.getIn().getHeader(FILE_NAME_HEADER));
          }
        });
  }
}
