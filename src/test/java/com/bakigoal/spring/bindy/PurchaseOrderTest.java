package com.bakigoal.spring.bindy;

import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * Created by ilmir on 05.10.16.
 */
public class PurchaseOrderTest extends TestCase {

  private String username;
  private String password;
  private String mailTo;

  public PurchaseOrderTest() {
    Properties properties = new Properties();
    try (InputStream inputStream = new FileInputStream("src/test/resources/emailConfig.properties")) {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    username = properties.getProperty("emailUsername");
    password = properties.getProperty("emailPassword");
    mailTo = properties.getProperty("emailTo");
  }

  public void testBindy() throws Exception {
    //    start camel context
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(createCsvRoute());
    context.start();

    MockEndpoint mock = context.getEndpoint("mock:result", MockEndpoint.class);
    mock.expectedBodiesReceived("Camel in Action,49.95,1\n");

    //    create order
    PurchaseOrder order = new PurchaseOrder();
    order.setAmount(1);
    order.setPrice(new BigDecimal("49.95"));
    order.setName("Camel in Action");

    ProducerTemplate template = context.createProducerTemplate();
    //    template.sendBody("direct:toCsv", order);
    template.sendBodyAndHeader("direct:toCsv", order, "CamelFileName", "camel.csv");

    mock.assertIsSatisfied();
  }

  private RouteBuilder createCsvRoute() {
    return new RouteBuilder() {
      public void configure() throws Exception {
        from("direct:toCsv")
            .marshal().bindy(BindyType.Csv, PurchaseOrder.class)
            .multicast()
            .to("mock:result", "file://src/data/csv");
      }
    };
  }

  public void testEmail() throws Exception {
    //    start camel context
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(createEmailRoute());
    context.start();

    //    create order
    PurchaseOrder order = new PurchaseOrder();
    order.setAmount(2);
    order.setPrice(BigDecimal.valueOf(123.32));
    order.setName("Camel in Action");

    ProducerTemplate template = context.createProducerTemplate();
    template.sendBody("direct:sendEmail", order);
  }

  private RoutesBuilder createEmailRoute() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:sendEmail")
            .setHeader("To", constant(mailTo))
            .setHeader("Subject", constant("Thanks for ordering"))
            .setHeader("From", constant(username + "@mail.ru"))
            .to("velocity://email.vm")
            .to("smtps://smtp.mail.ru:465?username=" + username + "&password=" + password);
      }
    };
  }

}