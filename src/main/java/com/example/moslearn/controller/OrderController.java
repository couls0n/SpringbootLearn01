package com.example.moslearn.controller;

import com.example.moslearn.dto.CreateOrderRequest;
import com.example.moslearn.dto.CreateUserRequest;
import com.example.moslearn.dto.UpdateOrderRequest;
import com.example.moslearn.model.Order;
import com.example.moslearn.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    private List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    private Order getOrderById(@PathVariable long id) {
        return orderService.findById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//不要忘记@RequestBody注解，否则请求体中的数据无法正确绑定到方法参数上
    private Order createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.create(request);
    }

    @PutMapping("/{id}")
    private Order updateOrder(@PathVariable long id,@Valid @RequestBody UpdateOrderRequest request) {
        return orderService.update(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteOrder(@PathVariable long id) {
        orderService.delete(id);
    }

}
