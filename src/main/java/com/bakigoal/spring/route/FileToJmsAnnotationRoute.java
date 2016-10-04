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
  private static final String JMS_CONTINUED_PROCESSING = "jms:continuedProcessing";
  private static final String JMS_PRODUCTION = "jms:production";
  private static final String JMS_ACCOUNTING = "jms:accounting";

  private final String FILE_NAME_HEADER = "CamelFileName";

  public void configure() throws Exception {

    from(FILE_URI)
        .choice()
        .when(header(FILE_NAME_HEADER).endsWith(".xml")).to(JMS_XML_URI)
        .when(header(FILE_NAME_HEADER).regex("^.*(csv|csl)$")).to(JMS_CSV_URI)
        .otherwise().to(JMS_BAD_ORDER_URI).stop()
        .end()
        .to(JMS_CONTINUED_PROCESSING);

    from(JMS_XML_URI)
        .filter(xpath("/order[not(@test)]"))
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
        })
        .multicast().parallelProcessing().to(JMS_ACCOUNTING, JMS_PRODUCTION);

    from(JMS_CONTINUED_PROCESSING)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("===========================================================");
            System.out.println("Continue from: " + exchange.getIn().getHeader(FILE_NAME_HEADER));
            System.out.println("===========================================================");
          }
        });

    from(JMS_ACCOUNTING)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Accounting: " + exchange.getIn().getHeader(FILE_NAME_HEADER));
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
          }
        });
    from(JMS_PRODUCTION)
        .process(new Processor() {
          public void process(Exchange exchange) throws Exception {
            System.out.println("###########################################################");
            System.out.println("Production: " + exchange.getIn().getHeader(FILE_NAME_HEADER));
            System.out.println("###########################################################");
          }
        });
  }
}
