package com.exercicio.favoritos.entidades;

import java.io.Serializable;

import com.exercicio.favoritos.usuarios.entidades.Usuario;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class NumeroFavoritoId implements Serializable {

    @ManyToOne
    private Usuario usuario;

    private int numeroFavorito;

}