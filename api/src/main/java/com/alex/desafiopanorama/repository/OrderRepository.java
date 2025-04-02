package com.alex.desafiopanorama.repository;

import com.alex.desafiopanorama.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
    long countByClientId(Long clientId);
}

