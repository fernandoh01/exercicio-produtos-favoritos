package com.exercicio.favoritos.usuarios.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exercicio.favoritos.usuarios.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
