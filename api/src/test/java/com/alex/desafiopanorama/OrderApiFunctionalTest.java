package com.alex.desafiopanorama;

import com.alex.desafiopanorama.domain.Order;
import com.alex.desafiopanorama.domain.OrderItem;
import com.alex.desafiopanorama.dto.OrderCountResponse;
import com.alex.desafiopanorama.dto.OrderTotalResponse;
import com.alex.desafiopanorama.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrderApiFunctionalTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;


    @BeforeEach
    void setup() {
        orderRepository.deleteAll();

        Order order = new Order();
        order.setId(1001L);
        order.setClientId(1L);
        order.setItems(List.of(
                new OrderItem(null, "lápis", 100, 1.10),
                new OrderItem(null, "caderno", 10, 1.00)
        ));

        orderRepository.save(order);
    }

    @Test
    void shouldReturnTotalForOrder() {
        String url = "http://localhost:" + port + "/api/orders/1001/total";
        ResponseEntity<OrderTotalResponse> response = restTemplate.getForEntity(url, OrderTotalResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(response.getBody()).total()).isEqualTo(100 * 1.10 + 10 * 1.00);
    }

    @Test
    void orderNotFound() {
        String url = "http://localhost:" + port + "/api/orders/1002/total";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(404);

    }

    @Test
    void testInternalServerError() {
        String url = "http://localhost:" + port + "/api/orders/1002.0/total";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(500);

    }

    @Test
    void shouldReturnOrderCountByClient() {
        String url = "http://localhost:" + port + "/api/clients/1/orders/count";
        ResponseEntity<OrderCountResponse> response = restTemplate.getForEntity(url, OrderCountResponse.class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(Objects.requireNonNull(response.getBody()).count()).isEqualTo(1);
    }

    @Test
    void shouldReturnOrdersByClient() {
        String url = "http://localhost:" + port + "/api/clients/1/orders";
        ResponseEntity<Order[]> response = restTemplate.getForEntity(url, Order[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody()[0].getClientId()).isEqualTo(1L);
    }
}
