package com.senac.biblioteca.repository;

import com.senac.biblioteca.entity.Emprestimo;
import com.senac.biblioteca.enums.StatusEmprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Camada de acesso aos dados da entidade Emprestimo.
 *
 * JpaRepository fornece o CRUD e a paginação. O método personalizado abaixo
 * permite filtrar empréstimos pelo status informado.
 */
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    /**
     * Busca empréstimos por status, como ATIVO, DEVOLVIDO ou ATRASADO.
     * O resultado também é paginado.
     */
    Page<Emprestimo> findByStatus(StatusEmprestimo status, Pageable pageable);
}
