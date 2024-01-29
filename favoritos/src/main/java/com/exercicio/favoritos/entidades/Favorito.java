package com.exercicio.favoritos.entidades;

import com.exercicio.favoritos.usuarios.entidades.Usuario;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "favoritos")
@Entity(name = "Favorito")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Favorito {
    @EmbeddedId
    private NumeroFavoritoId id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;
}
