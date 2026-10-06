package com.senac.biblioteca.controller;

import com.senac.biblioteca.entity.Emprestimo;
import com.senac.biblioteca.enums.StatusEmprestimo;
import com.senac.biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Empréstimos", description = "Endpoints para gerenciamento dos empréstimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar empréstimos", description = "Retorna os empréstimos cadastrados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Empréstimos listados com sucesso")
    public Page<Emprestimo> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar empréstimo por ID", description = "Retorna um empréstimo pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empréstimo encontrado"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar empréstimo",
        description = "Cria um empréstimo para um usuário e um livro já cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos, usuário ou livro não encontrado")
    })
    public ResponseEntity<Emprestimo> criar(@Valid @RequestBody Emprestimo emprestimo) {
        return service.criar(emprestimo)
            .map(criado -> ResponseEntity.status(HttpStatus.CREATED).body(criado))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar empréstimo", description = "Atualiza datas e status de um empréstimo existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    public ResponseEntity<Emprestimo> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody Emprestimo emprestimo
    ) {
        return service.atualizar(id, emprestimo)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir empréstimo", description = "Exclui um empréstimo pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Empréstimo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar empréstimos por status", description = "Busca empréstimos pelo status informado.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public Page<Emprestimo> buscarPorStatus(
        @RequestParam StatusEmprestimo status,
        Pageable pageable
    ) {
        return service.buscarPorStatus(status, pageable);
    }
}
