package br.edu.iff.ccc.librekan.librekan.dto;

import jakarta.validation.constraints.NotBlank;

public class ListaUpdateDTO {

    @NotBlank(message = "O nome da lista não pode ser vazio")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}