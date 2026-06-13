package com.example.moslearn.dto;

import com.example.moslearn.model.OrderStatus;

public record OrderResponse(
        Long orderId,
        Long userId,
        Long amount,
        OrderStatus status,
        String createTime) {
}
