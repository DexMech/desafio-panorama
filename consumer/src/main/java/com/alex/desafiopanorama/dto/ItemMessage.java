package com.alex.desafiopanorama.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemMessage {
    private String produto;
    private Integer quantidade;
    private Double preco;
}
