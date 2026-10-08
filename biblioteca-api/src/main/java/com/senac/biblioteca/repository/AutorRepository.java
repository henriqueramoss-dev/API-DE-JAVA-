package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Camada de acesso aos dados da entidade Autor.
 *
 * JpaRepository já fornece as operações básicas de CRUD, como salvar,
 * buscar por ID, listar, atualizar e excluir registros.
 */
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Consulta personalizada exigida pelo projeto.
     * Procura registros pelo campo nome, ignorando diferença entre maiúsculas
     * e minúsculas, e devolve o resultado de forma paginada.
     */
    Page<Autor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
