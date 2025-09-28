package br.edu.iff.ccc.librekan.librekan.dto;

public class QuadroDTO {

    private Long id;
    private String nome;

    public QuadroDTO() {
    }

    public QuadroDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}