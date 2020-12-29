package com.online.store.services;

import com.online.store.models.DeliveryInfo;

public interface DeliveryService {
    boolean validateDeliveryInfo(DeliveryInfo deliveryInfo);
}
