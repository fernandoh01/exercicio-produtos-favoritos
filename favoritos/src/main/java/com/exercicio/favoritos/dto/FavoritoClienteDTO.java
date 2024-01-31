package com.exercicio.favoritos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FavoritoClienteDTO {
    private String idProduto;
    private String titulo;
    private String imagem;
    private Float preco;
}
