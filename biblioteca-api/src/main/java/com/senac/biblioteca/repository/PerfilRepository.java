package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Camada de acesso aos dados da entidade Perfil.
 *
 * JpaRepository já fornece as operações básicas de CRUD, como salvar,
 * buscar por ID, listar, atualizar e excluir registros.
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    /**
     * Consulta personalizada exigida pelo projeto.
     * Procura registros pelo campo telefone, ignorando diferença entre maiúsculas
     * e minúsculas, e devolve o resultado de forma paginada.
     */
    Page<Perfil> findByTelefoneContainingIgnoreCase(String telefone, Pageable pageable);
}
