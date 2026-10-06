package com.senac.biblioteca.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Biblioteca API",
        version = "1.0",
        description = "API REST para gerenciamento de usuários, perfis, autores, categorias, livros e empréstimos de uma biblioteca."
    )
)
public class OpenApiConfig {
}
