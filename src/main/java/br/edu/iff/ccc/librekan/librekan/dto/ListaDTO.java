package br.edu.iff.ccc.librekan.librekan.dto;

public class ListaDTO {
    private Long id;
    private String nome;

    public ListaDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}