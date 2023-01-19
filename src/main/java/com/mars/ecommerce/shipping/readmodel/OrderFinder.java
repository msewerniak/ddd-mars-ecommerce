package com.mars.ecommerce.shipping.readmodel;

import java.util.UUID;

public interface OrderFinder {

    OrderDto find(UUID id);
}
