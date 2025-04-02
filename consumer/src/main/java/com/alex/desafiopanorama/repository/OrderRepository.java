package com.alex.desafiopanorama.repository;



import com.alex.desafiopanorama.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
