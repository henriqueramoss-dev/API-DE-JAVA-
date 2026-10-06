package com.senac.biblioteca.controller;

import com.senac.biblioteca.assembler.UsuarioModelAssembler;
import com.senac.biblioteca.entity.Usuario;
import com.senac.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários da biblioteca")
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioModelAssembler assembler;
    private final PagedResourcesAssembler<Usuario> pagedResourcesAssembler;

    public UsuarioController(
        UsuarioService service,
        UsuarioModelAssembler assembler,
        PagedResourcesAssembler<Usuario> pagedResourcesAssembler
    ) {
        this.service = service;
        this.assembler = assembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    @Operation(
        summary = "Listar usuarios",
        description = "Retorna os usuarios cadastrados de forma paginada."
    )
    @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso")
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> listar(
        @ParameterObject @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<Usuario> pagina = service.listar(pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, assembler));
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar usuario por ID",
        description = "Retorna um usuario a partir do seu identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar usuário",
        description = "Cria um novo usuário na biblioteca."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<EntityModel<Usuario>> criar(@Valid @RequestBody Usuario item) {
        Usuario criado = service.criar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(criado));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar usuario",
        description = "Atualiza o nome e o e-mail de um usuário existente."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> atualizar(
        @PathVariable Long id,
        @Valid @RequestBody Usuario item
    ) {
        return service.atualizar(id, item)
            .map(assembler::toModel)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Excluir usuario",
        description = "Exclui um usuario pelo seu identificador."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuario não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(
        summary = "Buscar usuários por nome",
        description = "Retorna usuários cujo nome contém o texto informado, usando paginação."
    )
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    public ResponseEntity<PagedModel<EntityModel<Usuario>>> buscar(
        @RequestParam String nome,
        @ParameterObject @PageableDefault(size = 10, page = 0, sort = "id") Pageable pageable
    ) {
        Page<Usuario> pagina = service.buscarPorNome(nome, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(pagina, assembler));
    }
}
