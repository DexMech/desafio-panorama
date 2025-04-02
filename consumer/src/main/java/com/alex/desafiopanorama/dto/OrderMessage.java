package com.alex.desafiopanorama.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {
    private Long codigoPedido;
    private Long codigoCliente;
    private List<ItemMessage> itens;
}
