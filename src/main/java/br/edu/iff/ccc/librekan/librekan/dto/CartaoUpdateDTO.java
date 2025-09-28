package br.edu.iff.ccc.librekan.librekan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CartaoUpdateDTO {

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 255)
    private String titulo;

    @Size(max = 1000)
    private String descricao;
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}