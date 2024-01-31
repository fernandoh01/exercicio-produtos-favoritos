package com.exercicio.favoritos.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.exercicio.favoritos.dto.FavoritoClienteDTO;
import com.exercicio.favoritos.dto.RequisicaoFavoritoClienteDTO;
import com.exercicio.favoritos.entidades.Favorito;
import com.exercicio.favoritos.entidades.NumeroFavoritoId;
import com.exercicio.favoritos.repositorios.FavoritoRepositorio;
import com.exercicio.favoritos.usuarios.entidades.Usuario;
import com.exercicio.favoritos.usuarios.repositorios.UsuarioRepositorio;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoritoController {
    private final FavoritoRepositorio favoritoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    FavoritoController(FavoritoRepositorio FavRep, UsuarioRepositorio uRep){
        this.favoritoRepositorio = FavRep;
        this.usuarioRepositorio = uRep;
    }

    @PostMapping("/favoritos")
    ResponseEntity<FavoritoClienteDTO> novoFavorito (@RequestBody RequisicaoFavoritoClienteDTO requisicao){
        Usuario usuario = this.usuarioRepositorio.findById(requisicao.getClienteId()).get();
        NumeroFavoritoId numFav = new NumeroFavoritoId(usuario, requisicao.getProdutoId());
        Favorito fav = new Favorito(numFav, usuario);
        this.favoritoRepositorio.save(fav);
        FavoritoClienteDTO res = new FavoritoClienteDTO(requisicao.getProdutoId(), "Titulo Fake",
        "/image_link", 32.23f);
        return ResponseEntity.ok(res);
    }
}
