package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO para criar ou atualizar os dados de um cartão")
public class CartaoUpdateDTO {

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 255)
    @Schema(description = "Título do cartão", example = "Desenvolver frontend da tela de login")
    private String titulo;

    @Size(max = 1000)
    @Schema(description = "Descrição detalhada do cartão (opcional)", example = "Usar HTML, Tailwind CSS e JavaScript.")
    private String descricao;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}