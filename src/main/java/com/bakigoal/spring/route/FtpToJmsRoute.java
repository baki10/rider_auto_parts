package com.bakigoal.spring.route;

import com.bakigoal.DownloadLoggerProcessor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by ilmir on 04.10.16.
 */
public class FtpToJmsRoute extends RouteBuilder {

  private static final String FTP_URI = "file:src/data?noop=true";
  private static final String JMS_URI = "jms:queue:incomingOrders";

  public void configure() throws Exception {
    from(FTP_URI).process(new DownloadLoggerProcessor()).to(JMS_URI);
  }
}
