package com.alex.desafiopanorama;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.dto.ItemMessage;
import com.alex.desafiopanorama.dto.OrderMessage;
import com.alex.desafiopanorama.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
class ConsumerFunctionalTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    void shouldConsumeOrderAndPersistToDatabase() {
        ItemMessage item = ItemMessage.builder()
                .preco(3000.0)
                .produto("notebook")
                .quantidade(1)
                .build();
        OrderMessage orderMessage = new OrderMessage(10L, 999L, List.of(item));


        rabbitTemplate.convertAndSend("order-queue", orderMessage);


        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            Order savedOrder = orderRepository.findById(10L).orElseThrow();
            assertEquals(999L, savedOrder.getClientId());
        });
    }

    @Test
    void shouldNotPersistInvalidOrder() {
        ItemMessage item = ItemMessage.builder()
                .preco(3000.0)
                .produto("notebook")
                .quantidade(1)
                .build();
        OrderMessage invalidOrder = new OrderMessage(10L, null, List.of(item));

        rabbitTemplate.convertAndSend("order-queue", invalidOrder);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
        assertThat(orderRepository.findById(100L)).isEmpty();
        });

    }
}

