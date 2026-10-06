package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.LivroController;
import com.senac.biblioteca.entity.Livro;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class LivroModelAssembler implements RepresentationModelAssembler<Livro, EntityModel<Livro>> {

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
