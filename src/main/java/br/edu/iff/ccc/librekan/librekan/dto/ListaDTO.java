package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar os dados de uma lista em respostas da API")
public class ListaDTO {
    @Schema(description = "ID único da lista", example = "10")
    private Long id;

    @Schema(description = "Nome da lista", example = "A Fazer")
    private String nome;

    public ListaDTO(Long id, String nome) { this.id = id; this.nome = nome; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}