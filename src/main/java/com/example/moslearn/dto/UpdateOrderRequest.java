package com.example.moslearn.dto;

import com.example.moslearn.model.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest(
        @NotBlank
        long userId,
        @NotBlank
        String orderId,
//        int status)
        @NotNull
        OrderStatus status)
{
}
