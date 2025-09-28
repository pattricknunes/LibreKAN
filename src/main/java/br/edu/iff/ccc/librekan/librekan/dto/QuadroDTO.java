package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar os dados de um quadro em respostas da API")
public class QuadroDTO {

    @Schema(description = "ID único do quadro", example = "1")
    private Long id;

    @Schema(description = "Nome do quadro", example = "Projeto de Redes")
    private String nome;

    public QuadroDTO() {}
    public QuadroDTO(Long id, String nome) { this.id = id; this.nome = nome; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}