package com.exercicio.favoritos.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exercicio.favoritos.entidades.Favorito;
import com.exercicio.favoritos.entidades.NumeroFavoritoId;

public interface FavoritoRepositorio extends JpaRepository<Favorito, NumeroFavoritoId>{
    
    @Query("from Favorito where usuario.id = :user_id")
    public List<Favorito> findByUserId(@Param("user_id") long user_id);
    
    @Query("from Favorito where usuario.id = :user_id and id.numeroFavorito = :product_id")
    public Optional<Favorito> findByUserIdAndProductId(@Param("user_id") long user_id, 
        @Param("product_id") String product_id);

    @Modifying
    @Query("delete from Favorito f where f.usuario.id=:userId")
    public void deleteByUser(@Param("userId") Long userId);
}
