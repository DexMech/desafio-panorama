package com.alex.desafiopanorama.usecase;


import com.alex.desafiopanorama.domain.Order;

public interface ProcessOrderUseCase {
    void execute(Order order);
}