package com.senac.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade usada para classificar os livros do acervo.
 * Exemplos: Romance, Tecnologia, História e Ficção.
 */
@Entity
@Table(name = "categorias")
@Schema(description = "Categoria utilizada para classificar livros do acervo")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome obrigatório da categoria.
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    @Schema(description = "Nome da categoria", example = "Romance")
    private String nome;

    // One-to-Many: uma categoria pode possuir vários livros.
    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Livro> livros = new ArrayList<>();

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Livro> getLivros() {
        return livros;
    }
}
