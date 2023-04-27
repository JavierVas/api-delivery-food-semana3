package com.example.demo.service;


import com.example.demo.modelo.Food;
import com.example.demo.modelo.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl extends OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order createOrder(Order order) {
        order.setCreationTime(LocalDateTime.now());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(newStatus);
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order addItemToOrder(Long orderId, Food food) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<Food> items = order.getItems();
            items.add(food);
            order.setItems(items);
            return orderRepository.save(order);
        }
        return null;
    }
}
