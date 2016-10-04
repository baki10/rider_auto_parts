package com.bakigoal.spring.route;

import com.bakigoal.DownloadLoggerProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by ilmir on 04.10.16.
 */
@Component
public class FtpToJmsAnnotationRoute extends SpringRouteBuilder {

  private static final String FTP_URI = "file:src/data?noop=true";
  private static final String JMS_URI = "jms:queue:incomingOrders";

  public void configure() throws Exception {
    from(FTP_URI).process(new DownloadLoggerProcessor()).to(JMS_URI);
  }
}
