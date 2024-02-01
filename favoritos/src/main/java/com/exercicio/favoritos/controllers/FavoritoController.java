package com.exercicio.favoritos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.exercicio.favoritos.dto.FavoritoClienteDTO;
import com.exercicio.favoritos.dto.FavoritoClienteSemIdDTO;
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

    @GetMapping("/clientes/{cliente_id}/favoritos")
    ResponseEntity<List<FavoritoClienteDTO>> recuperaTodosFavoritos(@PathVariable Long cliente_id){
        List<Favorito> favoritos_usuario = this.favoritoRepositorio.findByUserId(cliente_id);
        if(favoritos_usuario == null || favoritos_usuario.size() == 0){
            return ResponseEntity.noContent().build();
        }
        List<FavoritoClienteDTO> favoritos = new ArrayList<>();
        for (Favorito favorito : favoritos_usuario) {
            favoritos.add(ProdutoDados.resgataDadosProduto(favorito.getId().getNumeroFavorito()));
        }
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/clientes/{cliente_id}/favoritos/{produto_id}")
    ResponseEntity<FavoritoClienteSemIdDTO> recuperaTodosFavoritos(@PathVariable Long cliente_id,
        @PathVariable String produto_id){
        FavoritoClienteDTO res = ProdutoDados.resgataDadosProduto(produto_id);
        if(res == null)
            return ResponseEntity.noContent().build();
        else{
            return ResponseEntity.ok(
                new FavoritoClienteSemIdDTO(res.getTitulo(), res.getImagem(), res.getPreco()));
        }
    }

    @PostMapping("/clientes/{cliente_id}/favoritos")
    ResponseEntity<FavoritoClienteDTO> novoFavorito (@PathVariable Long cliente_id, @RequestBody RequisicaoFavoritoClienteDTO produdoId){
        
        Usuario usuario = this.usuarioRepositorio.findById(cliente_id).get();
        Optional<Favorito> favorito_existente = this.favoritoRepositorio.findByUserIdAndProductId(cliente_id, produdoId.getProdutoId());
        if(favorito_existente.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        FavoritoClienteDTO res = ProdutoDados.resgataDadosProduto(produdoId.getProdutoId());
        if(res == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        NumeroFavoritoId numFav = new NumeroFavoritoId(usuario, produdoId.getProdutoId());
        Favorito fav = new Favorito(numFav, usuario);
        this.favoritoRepositorio.save(fav);
        
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/clientes/{cliente_id}/favoritos")
    ResponseEntity<?> removeFavorito(@PathVariable Long cliente_id, @RequestBody RequisicaoFavoritoClienteDTO produdoId){
        Optional<Favorito> res = this.favoritoRepositorio.findByUserIdAndProductId(cliente_id, produdoId.getProdutoId());
        if(res.isPresent()){
            this.favoritoRepositorio.delete(res.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
