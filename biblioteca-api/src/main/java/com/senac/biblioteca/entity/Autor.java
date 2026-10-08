package com.senac.biblioteca.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um autor cadastrado no acervo.
 * Um autor pode participar de vários livros.
 */
@Entity
@Table(name = "autores")
@Schema(description = "Autor responsável por uma ou mais obras do acervo")
public class Autor {

    // Chave primária gerada automaticamente pelo banco.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome obrigatório do autor, validado antes de salvar.
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome completo do autor", example = "Machado de Assis")
    private String nome;

    // Lado inverso do Many-to-Many: um autor pode estar relacionado a vários livros.
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros = new HashSet<>();

    public Autor() {
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

    public Set<Livro> getLivros() {
        return livros;
    }
}
