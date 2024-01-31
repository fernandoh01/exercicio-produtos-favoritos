package com.exercicio.favoritos.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercicio.favoritos.entidades.Favorito;
import com.exercicio.favoritos.entidades.NumeroFavoritoId;

public interface FavoritoRepositorio extends JpaRepository<Favorito, NumeroFavoritoId>{
    
}
