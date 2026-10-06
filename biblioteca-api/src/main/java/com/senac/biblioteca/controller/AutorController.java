package com.senac.biblioteca.controller;

import com.senac.biblioteca.entity.Autor;
import com.senac.biblioteca.service.AutorService;
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
@RequestMapping("/autores")
@Tag(name = "Autores", description = "Endpoints para gerenciamento dos autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar autores", description = "Retorna os autores cadastrados de forma paginada.")
    @ApiResponse(responseCode = "200", description = "Autores listados com sucesso")
    public Page<Autor> listar(Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID", description = "Retorna um autor pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor encontrado"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastrar autor", description = "Cria um novo autor na biblioteca.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Autor> criar(@Valid @RequestBody Autor autor) {
        Autor criado = service.criar(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar autor", description = "Atualiza o nome de um autor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Autor> atualizar(@PathVariable Long id, @Valid @RequestBody Autor autor) {
        return service.atualizar(id, autor)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir autor", description = "Exclui um autor pelo seu identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar autores por nome", description = "Busca autores cujo nome contém o texto informado.")
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public Page<Autor> buscarPorNome(@RequestParam String nome, Pageable pageable) {
        return service.buscarPorNome(nome, pageable);
    }
}
