package com.senac.biblioteca.enums;

/**
 * Define os estados possíveis de disponibilidade de um livro.
 *
 * O enum mantém os valores padronizados e facilita a leitura do código.
 */
public enum StatusLivro {

    // O livro está disponível para empréstimo.
    DISPONIVEL,

    // O livro está atualmente emprestado.
    EMPRESTADO,

    // O livro não pode ser emprestado no momento.
    INDISPONIVEL
}
