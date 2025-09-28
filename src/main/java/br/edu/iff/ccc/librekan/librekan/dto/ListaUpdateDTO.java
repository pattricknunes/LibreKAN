package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para criar ou atualizar o nome de uma lista")
public class ListaUpdateDTO {

    @NotBlank(message = "O nome da lista não pode ser vazio")
    @Schema(description = "Novo nome da lista", example = "Em Andamento")
    private String nome;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}