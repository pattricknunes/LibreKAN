package br.edu.iff.ccc.librekan.librekan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuadroUpdateDTO {

    @NotBlank(message = "O nome do quadro não pode ser vazio")
    @Size(max = 100, message = "O nome do quadro deve ter no máximo 100 caracteres")
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}