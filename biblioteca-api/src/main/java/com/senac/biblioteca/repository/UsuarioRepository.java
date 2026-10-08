package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository de Usuario.
 * JpaRepository fornece CRUD e paginação; o método abaixo cria a busca personalizada por nome.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
