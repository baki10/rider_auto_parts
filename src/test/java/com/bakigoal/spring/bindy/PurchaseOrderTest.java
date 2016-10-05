package com.bakigoal.spring.bindy;

import junit.framework.TestCase;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.BindyType;

import java.math.BigDecimal;

/**
 * Created by ilmir on 05.10.16.
 */
public class PurchaseOrderTest extends TestCase {

  public void testBindy() throws Exception {
    //    start camel context
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(createRoute());
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

  public RouteBuilder createRoute() {
    return new RouteBuilder() {
      public void configure() throws Exception {
        from("direct:toCsv")
            .marshal().bindy(BindyType.Csv, PurchaseOrder.class)
            .multicast()
            .to("mock:result", "file://src/data/csv");
      }
    };
  }

}