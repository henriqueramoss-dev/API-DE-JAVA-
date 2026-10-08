package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.LivroController;
import com.senac.biblioteca.entity.Livro;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Responsável por transformar um objeto Livro em um EntityModel HATEOAS.
 *
 * O EntityModel mantém os dados da entidade e acrescenta links que ajudam
 * o cliente da API a navegar entre as operações disponíveis.
 */
@Component
public class LivroModelAssembler
        implements RepresentationModelAssembler<Livro, EntityModel<Livro>> {

    /**
     * Adiciona os links HATEOAS à resposta da entidade.
     *
     * self   -> consulta o próprio recurso
     * update -> indica a rota utilizada para atualização
     * delete -> indica a rota utilizada para exclusão
     * livros -> retorna para a listagem paginada do recurso
     */
    @Override
    public EntityModel<Livro> toModel(Livro item) {
        return EntityModel.of(
            item,
            linkTo(LivroController.class).slash(item.getId()).withSelfRel(),
            linkTo(LivroController.class).slash(item.getId()).withRel("update"),
            linkTo(LivroController.class).slash(item.getId()).withRel("delete"),
            linkTo(LivroController.class).withRel("livros")
        );
    }
}
