package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criar ou atualizar o nome de um quadro")
public class QuadroUpdateDTO {

    @NotBlank(message = "O nome do quadro não pode ser vazio")
    @Size(max = 100, message = "O nome do quadro deve ter no máximo 100 caracteres")
    @Schema(description = "Novo nome do quadro", example = "Projeto de Sistemas Operacionais")
    private String nome;
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}