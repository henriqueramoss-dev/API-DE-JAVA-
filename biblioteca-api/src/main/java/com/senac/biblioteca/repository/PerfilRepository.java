package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository de Perfil.
 * Herda o CRUD do JpaRepository e adiciona a busca paginada por telefone.
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Page<Perfil> findByTelefoneContainingIgnoreCase(String telefone, Pageable pageable);
}
