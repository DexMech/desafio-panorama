package com.alex.desafiopanorama.usecase;

import com.alex.desafiopanorama.domain.Order;

import java.util.List;

public interface GetOrdersByClientUseCase {
    List<Order> execute(Long clientId);
}
