package com.bakigoal.spring.service;

import com.bakigoal.spring.bindy.PurchaseOrder;

/**
 * Created by ilmir on 05.10.16.
 */
public interface OrderService {
  PurchaseOrder lookup(String id);
}
