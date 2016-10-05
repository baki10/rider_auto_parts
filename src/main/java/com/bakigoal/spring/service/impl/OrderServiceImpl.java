package com.bakigoal.spring.service.impl;

import com.bakigoal.spring.bindy.PurchaseOrder;
import com.bakigoal.spring.service.OrderService;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by ilmir on 05.10.16.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
  public PurchaseOrder lookup(@Header(value = "id") String id) {
    PurchaseOrder order = new PurchaseOrder();
    order.setName("Test order " + id);
    order.setPrice(BigDecimal.valueOf(12.2));
    order.setAmount(1);
    return order;
  }
}
