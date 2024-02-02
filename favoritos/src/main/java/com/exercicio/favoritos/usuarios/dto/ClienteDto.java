package com.exercicio.favoritos.usuarios.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClienteDto {

    @NotBlank(message = "Campo email é obrigatório")
    @Size(min = 3, max = 200)
    @Email
    private String email;

    @NotBlank(message = "Campo nome é obrigatório")
    @Size(min = 3, max = 200)
    private String nome;
}
