package com.alex.desafiopanorama.controller;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.dto.OrderCountResponse;
import com.alex.desafiopanorama.dto.OrderTotalResponse;
import com.alex.desafiopanorama.usecase.GetOrderCountByClientUseCase;
import com.alex.desafiopanorama.usecase.GetOrderTotalUseCase;
import com.alex.desafiopanorama.usecase.GetOrdersByClientUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class OrderController {

    private final GetOrderTotalUseCase getOrderTotalUseCase;
    private final GetOrderCountByClientUseCase getOrderCountByClientUseCase;
    private final GetOrdersByClientUseCase getOrdersByClientUseCase;

    public OrderController(GetOrderTotalUseCase getOrderTotalUseCase,
                           GetOrderCountByClientUseCase getOrderCountByClientUseCase,
                           GetOrdersByClientUseCase getOrdersByClientUseCase) {
        this.getOrderTotalUseCase = getOrderTotalUseCase;
        this.getOrderCountByClientUseCase = getOrderCountByClientUseCase;
        this.getOrdersByClientUseCase = getOrdersByClientUseCase;
    }

    @GetMapping("/orders/{id}/total")
    public ResponseEntity<OrderTotalResponse> getTotal(@PathVariable Long id) {
        log.info("getTotal order #{}", id);
        Double total = getOrderTotalUseCase.execute(id);
        return ResponseEntity.ok(new OrderTotalResponse(total));
    }

    @GetMapping("/clients/{clientId}/orders/count")
    public ResponseEntity<OrderCountResponse> countByClient(@PathVariable Long clientId) {
        log.info("countByClient #{}", clientId);
        long countByClient = getOrderCountByClientUseCase.execute(clientId);
        return ResponseEntity.ok(new OrderCountResponse(countByClient));
    }

    @GetMapping("/clients/{clientId}/orders")
    public List<Order> getOrdersByClient(@PathVariable Long clientId) {
        log.info("getOrdersByClient #{}", clientId);
        return getOrdersByClientUseCase.execute(clientId);
    }
}