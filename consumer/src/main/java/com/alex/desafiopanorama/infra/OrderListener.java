package com.alex.desafiopanorama.infra;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.domain.OrderItem;
import com.alex.desafiopanorama.dto.OrderMessage;
import com.alex.desafiopanorama.usecase.ProcessOrderUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderListener {

    private final ProcessOrderUseCase processOrderUseCase;

    public OrderListener(ProcessOrderUseCase processOrderUseCase) {
        this.processOrderUseCase = processOrderUseCase;
    }

    @RabbitListener(queues = "order-queue")
    public void consume(OrderMessage message) {
        try {
            Order order = new Order(
                    message.getCodigoPedido(),
                    message.getCodigoCliente(),
                    message.getItens().stream()
                            .map(i -> OrderItem
                                    .builder()
                                    .product(i.getProduto())
                                    .quantity(i.getQuantidade())
                                    .price(i.getPreco())
                                    .build())
                            .collect(Collectors.toList())
            );
            log.info("consuming order #{}", order.getId());
            processOrderUseCase.execute(order);
        }catch (Exception e) {
            log.error("error consuming order #{} - {}", message.getCodigoPedido(), e.getMessage());
        }

    }
}
