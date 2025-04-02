package com.alex.desafiopanorama.usecase.impl;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.repository.OrderRepository;
import com.alex.desafiopanorama.usecase.GetOrderTotalUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetOrderTotalUseCaseImpl implements GetOrderTotalUseCase {

    private final OrderRepository orderRepository;

    public GetOrderTotalUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Double execute(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}