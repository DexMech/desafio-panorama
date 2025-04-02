package com.alex.desafiopanorama.usecase.impl;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.repository.OrderRepository;
import com.alex.desafiopanorama.usecase.GetOrdersByClientUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersByClientUseCaseImpl implements GetOrdersByClientUseCase {

    private final OrderRepository orderRepository;

    public GetOrdersByClientUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> execute(Long clientId) {
        return orderRepository.findByClientId(clientId);
    }
}