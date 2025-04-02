package com.alex.desafiopanorama.usecase.impl;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.repository.OrderRepository;
import com.alex.desafiopanorama.usecase.ProcessOrderUseCase;
import org.springframework.stereotype.Service;

@Service
public class ProcessOrderUseCaseImpl implements ProcessOrderUseCase {

    private final OrderRepository orderRepository;

    public ProcessOrderUseCaseImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute(Order order) {
        orderRepository.save(order);
    }
}
