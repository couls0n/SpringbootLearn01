package com.example.moslearn.repository;

import com.example.moslearn.dto.OrderResponse;
import com.example.moslearn.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUserId(Long userId);
}
