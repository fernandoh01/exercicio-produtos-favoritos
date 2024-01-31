package com.exercicio.favoritos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RequisicaoFavoritoClienteDTO {
    private Long clienteId;
    private String produtoId;
}
