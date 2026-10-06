package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Page<Perfil> findByTelefoneContainingIgnoreCase(String telefone, Pageable pageable);
}
