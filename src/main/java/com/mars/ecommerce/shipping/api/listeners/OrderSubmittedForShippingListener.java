package com.mars.ecommerce.shipping.api.listeners;

import com.mars.ecommerce.shipping.readmodel.OrderFinder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class OrderSubmittedForShippingListener {

    private final OrderFinder orderFinder;
    
}
