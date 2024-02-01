package com.exercicio.favoritos.usuarios.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exercicio.favoritos.entidades.Favorito;
import com.exercicio.favoritos.repositorios.FavoritoRepositorio;
import com.exercicio.favoritos.usuarios.dto.ClienteDto;
import com.exercicio.favoritos.usuarios.dto.ClienteIdDTO;
import com.exercicio.favoritos.usuarios.entidades.Usuario;
import com.exercicio.favoritos.usuarios.repositorios.UsuarioRepositorio;

@RestController
public class ClienteController {

    private final UsuarioRepositorio usuarioRepositorio;
    private final FavoritoRepositorio favoritoRepositorio;

    ClienteController(UsuarioRepositorio rep, FavoritoRepositorio favRep){
        this.usuarioRepositorio = rep;
        this.favoritoRepositorio = favRep;
    }

    @GetMapping("/clientes")
    ResponseEntity<List<Usuario>> recuperaTodosClientes(){
        try {
            List<Usuario> res =  this.usuarioRepositorio.findAll();
            if(res == null || res.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return ResponseEntity.ok(res);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }

    private static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    @GetMapping("/clientes/{cliente_id}")
    ResponseEntity<?> recuperaClientePeloId(@PathVariable Long cliente_id){
        try {
            Optional<Usuario> res = this.usuarioRepositorio.findById(cliente_id);
            if (res.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return ResponseEntity.ok(res.get());
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(String.format("Error: \n%s", 
                ClienteController.getStackTraceAsString(e)));
        }
    }

    @PostMapping("/clientes")
    ResponseEntity<Usuario> novoCliente(@RequestBody ClienteDto novoCliente){
        try {
            Optional<Usuario> jaCadastrado = this.usuarioRepositorio.findByEmail(novoCliente.getEmail());
            if(jaCadastrado.isPresent()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
            }
            Usuario add = new Usuario();
            add.setNome(novoCliente.getNome());
            add.setEmail(novoCliente.getEmail());
            Usuario usuarioCriado =  this.usuarioRepositorio.save(add);
            return ResponseEntity.ok(usuarioCriado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/clientes/{cliente_id}")
    ResponseEntity<Usuario> atualizaDadosClientes(@PathVariable Long cliente_id, @RequestBody ClienteDto clienteAtualizado){
        try {
            Optional<Usuario> jaCadastrado = this.usuarioRepositorio.findByEmail(clienteAtualizado.getEmail());
            if(jaCadastrado.isPresent()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
            }
            Optional<Usuario> res = this.usuarioRepositorio.findById(cliente_id);
            if (res.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else{
                Usuario u = res.get();
                u.setNome(clienteAtualizado.getNome());
                u.setEmail(clienteAtualizado.getEmail());
                this.usuarioRepositorio.save(u);
                return ResponseEntity.ok(res.get());
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/clientes")
    ResponseEntity<?> removeCliente(@RequestBody ClienteIdDTO cliente_id){
        Optional<Usuario> u =  this.usuarioRepositorio.findById(cliente_id.getClienteId());
        if (u.isPresent()){
            List<Favorito> favoritosUsuario =  this.favoritoRepositorio.findByUserId(u.get().getId());
            this.favoritoRepositorio.deleteAll(favoritosUsuario);
            this.usuarioRepositorio.delete(u.get());
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
