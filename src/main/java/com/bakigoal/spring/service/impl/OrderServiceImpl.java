package com.bakigoal.spring.service.impl;

import com.bakigoal.spring.bindy.PurchaseOrder;
import com.bakigoal.spring.service.OrderService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by ilmir on 05.10.16.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
  public PurchaseOrder lookup(@Header(name = "id") String id) {
    PurchaseOrder order = new PurchaseOrder();
    order.setName("Test order");
    order.setPrice(BigDecimal.valueOf(12.2));
    order.setAmount(1);
    return order;
  }
}
