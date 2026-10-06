package com.senac.biblioteca.controller;

import com.senac.biblioteca.entity.Livro;
import com.senac.biblioteca.service.LivroService;
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
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Endpoints para gerenciamento dos livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar livros", description = "Retorna os livros cadastrados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Livros listados com sucesso")
    public Page<Livro> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar livro por ID", description = "Retorna um livro pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar livro",
        description = "Cria um livro utilizando uma categoria e autores já cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos, categoria ou autor não encontrado")
    })
    public ResponseEntity<Livro> criar(@Valid @RequestBody Livro livro) {
        return service.criar(livro)
            .map(criado -> ResponseEntity.status(HttpStatus.CREATED).body(criado))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar livro", description = "Atualiza título, ISBN e ano de publicação de um livro.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        return service.atualizar(id, livro)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir livro", description = "Exclui um livro pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar livros por título", description = "Busca livros cujo título contém o texto informado.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public Page<Livro> buscarPorTitulo(@RequestParam String titulo, Pageable pageable) {
        return service.buscarPorTitulo(titulo, pageable);
    }
}
