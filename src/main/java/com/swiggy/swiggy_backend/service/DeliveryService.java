package com.swiggy.swiggy_backend.service;

import com.swiggy.swiggy_backend.dto.DeliveryRequest;
import com.swiggy.swiggy_backend.dto.DeliveryResponse;
import com.swiggy.swiggy_backend.entity.DeliveryStatus;

public interface DeliveryService {

    DeliveryResponse assignDelivery(DeliveryRequest request);

    DeliveryResponse getDeliveryByOrder(Long orderId);

    DeliveryResponse updateDeliveryStatus(Long deliveryId, DeliveryStatus status);

}