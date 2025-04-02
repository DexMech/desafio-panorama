package com.alex.desafiopanorama.usecase.impl;

import com.alex.desafiopanorama.repository.OrderRepository;
import com.alex.desafiopanorama.usecase.GetOrderCountByClientUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetOrderCountByClientUseCaseImpl implements GetOrderCountByClientUseCase {

    private final OrderRepository orderRepository;

    public GetOrderCountByClientUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long execute(Long clientId) {
        return orderRepository.countByClientId(clientId);
    }
}