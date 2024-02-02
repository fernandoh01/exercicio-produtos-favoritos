package com.exercicio.favoritos;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercicio.favoritos.dto.FavoritoClienteDTO;
import com.exercicio.favoritos.servicos.ProdutoDados;

@SpringBootTest
public class ProdutoDadosTest {
     
    @Test
	void testRecuperaDadosComSucesso() {
        FavoritoClienteDTO res =  ProdutoDados.resgataDadosProduto("6a512e6c-6627-d286-5d18-583558359ab6");
        assertNotNull(res);
        assertTrue(res.getTitulo().equalsIgnoreCase("Moisés Dorel Windoo 1529"));
	}

    @Test
	void testRecuperaDadosSemSucesso() {
        FavoritoClienteDTO res =  ProdutoDados.resgataDadosProduto("6a512e6c-6627-d286-5d18-583558359ab6-xxx");
        assertNull(res);
	}
}
