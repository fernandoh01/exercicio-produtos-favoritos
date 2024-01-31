package com.exercicio.favoritos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.exercicio.favoritos.dto.FavoritoClienteDTO;
import com.exercicio.favoritos.dto.RequisicaoFavoritoClienteDTO;
import com.exercicio.favoritos.entidades.Favorito;
import com.exercicio.favoritos.entidades.NumeroFavoritoId;
import com.exercicio.favoritos.repositorios.FavoritoRepositorio;
import com.exercicio.favoritos.servicos.ProdutoDados;
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
        FavoritoClienteDTO res = ProdutoDados.resgataDadosProduto(requisicao.getProdutoId());
        if(res == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        NumeroFavoritoId numFav = new NumeroFavoritoId(usuario, requisicao.getProdutoId());
        Favorito fav = new Favorito(numFav, usuario);
        this.favoritoRepositorio.save(fav);
        
        return ResponseEntity.ok(res);
    }
}
