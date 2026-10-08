package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Camada de acesso aos dados da entidade Livro.
 *
 * JpaRepository já fornece as operações básicas de CRUD, como salvar,
 * buscar por ID, listar, atualizar e excluir registros.
 */
public interface LivroRepository extends JpaRepository<Livro, Long> {

    /**
     * Consulta personalizada exigida pelo projeto.
     * Procura registros pelo campo titulo, ignorando diferença entre maiúsculas
     * e minúsculas, e devolve o resultado de forma paginada.
     */
    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
