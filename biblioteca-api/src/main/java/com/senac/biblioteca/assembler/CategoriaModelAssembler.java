package com.senac.biblioteca.assembler;

import com.senac.biblioteca.controller.CategoriaController;
import com.senac.biblioteca.entity.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria item) {
        return EntityModel.of(
            item,
            linkTo(CategoriaController.class).slash(item.getId()).withSelfRel(),
            linkTo(CategoriaController.class).slash(item.getId()).withRel("update"),
            linkTo(CategoriaController.class).slash(item.getId()).withRel("delete"),
            linkTo(CategoriaController.class).withRel("categorias")
        );
    }
}
