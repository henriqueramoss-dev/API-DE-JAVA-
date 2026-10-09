package com.senac.biblioteca.entity;

import com.senac.biblioteca.enums.StatusEmprestimo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Entidade que registra o empréstimo de um livro para um usuário.
 * Ela liga as entidades Usuario e Livro e controla datas e status do empréstimo.
 */
@Entity
@Table(name = "emprestimos")
@Schema(description = "Registro de empréstimo de um livro para um usuário")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador gerado automaticamente pelo banco", accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @NotNull(message = "A data do empréstimo é obrigatória")
    @Schema(description = "Data em que o empréstimo foi realizado", example = "2026-10-06")
    private LocalDate dataEmprestimo;

    @NotNull(message = "A data prevista de devolução é obrigatória")
    @Schema(description = "Data prevista para devolução", example = "2026-10-20")
    private LocalDate dataPrevistaDevolucao;

    // O enum restringe o status a valores válidos definidos pelo sistema.
    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Situação atual do empréstimo", example = "ATIVO")
    private StatusEmprestimo status;

    // Many-to-One: um usuário pode possuir vários empréstimos.
    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Many-to-One: um livro pode ter vários registros de empréstimo ao longo do tempo.
    @NotNull(message = "O livro é obrigatório")
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    public Emprestimo() {
    }

    public Long getId() {
        return id;
    }

    /**
     * Usado pela camada Service para garantir que novos cadastros
     * sempre recebam o ID gerado automaticamente pelo banco.
     */
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
