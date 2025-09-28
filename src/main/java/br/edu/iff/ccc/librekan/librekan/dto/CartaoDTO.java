package br.edu.iff.ccc.librekan.librekan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar os dados de um cartão em respostas da API")
public class CartaoDTO {
    @Schema(description = "ID único do cartão", example = "101")
    private Long id;

    @Schema(description = "Título do cartão", example = "Modelar banco de dados")
    private String titulo;

    @Schema(description = "Descrição detalhada do cartão", example = "Definir todas as entidades e relacionamentos.")
    private String descricao;

    public CartaoDTO() {}
    public CartaoDTO(Long id, String titulo, String descricao) { this.id = id; this.titulo = titulo; this.descricao = descricao; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}