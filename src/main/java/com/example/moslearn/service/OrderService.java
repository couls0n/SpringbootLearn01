package com.example.moslearn.service;

import com.example.moslearn.dto.CreateOrderRequest;
import com.example.moslearn.dto.OrderResponse;
import com.example.moslearn.dto.UpdateOrderRequest;
import com.example.moslearn.exception.OrderNotFoundException;
import com.example.moslearn.exception.OrderProcessingException;
import com.example.moslearn.exception.UserNotFoundException;
import com.example.moslearn.model.Order;
import com.example.moslearn.model.OrderStatus;
import com.example.moslearn.model.User;
import com.example.moslearn.repository.OrderRepository;
import com.example.moslearn.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,StringRedisTemplate redisTemplate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(long id) {
        return requireOrder(id);
    }

    public Order create(@Valid CreateOrderRequest request) {
        User user = userRepository
                .findById(request.userId())
                .orElseThrow(() -> new OrderNotFoundException(request.userId()));
        Order order = new Order();
//        order.setUserId(request.userId());
        order.setUser(user);
        order.setAmount(request.amount());
        order.setStatus(OrderStatus.CREATED);
        order.setCreateTime(LocalDateTime.now().toString());
        return orderRepository.save(order);
    }

//    public Order update(long id, @Valid UpdateOrderRequest request) {
//        Order order = requireOrder(id);
//        order.setStatus(request.status());
//        return orderRepository.save(order);
//    }

    public void delete(long id) {
        orderRepository.delete(requireOrder(id));
    }
    @Transactional(readOnly = true)
    private Order requireOrder(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public List<OrderResponse> findUserOrders(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return orderRepository.findOrdersByUserId(id)
                .stream()
                .map(this::toResponse)
                .toList();
//        List<OrderResponse> result = new ArrayList<>();
//        for(Order order: orderRepository.findOrdersByUserId(id)) {
//            result.add(toResponse(order));
//        }
//        return result;
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getUser().getId(),
                order.getAmount(),
                order.getStatus(),
                order.getCreateTime()
        );
    }
    @Transactional
    public Order update(long id,@Valid UpdateOrderRequest request) {
        String lockKey = "lock:order:" + id;
        String lockValue = UUID.randomUUID().toString(); // 10秒后过期

        Boolean locked = redisTemplate
                            .opsForValue()
                            .setIfAbsent(lockKey, lockValue, Duration.ofSeconds(10));
        if(Boolean.FALSE.equals(locked)) {
            throw new OrderProcessingException("Order"+id+"is being processed by another request");
        }

        try {
            Order order = requireOrder(id);
            order.setStatus(request.status());
            return orderRepository.save(order);
        }finally {//防止锁到期删除别人的锁
            String currentValue = redisTemplate
                    .opsForValue()
                    .get(lockKey);
            if(lockValue.equals(currentValue)) {
                redisTemplate.delete(lockKey);
            }

        }

    }
}
