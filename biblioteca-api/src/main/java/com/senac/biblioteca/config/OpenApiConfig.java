package com.senac.biblioteca.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configura as informações gerais exibidas no Swagger/OpenAPI.
 * Esta classe personaliza nome, descrição, versão, contato e servidor da API.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Biblioteca - Gestão de Acervo e Empréstimos",
        version = "1.0.0",
        description = "API REST acadêmica para gerenciamento de usuários, perfis, autores, categorias, livros e empréstimos de uma biblioteca.",
        termsOfService = "https://github.com/henriqueramoss-dev/API-DE-JAVA-/blob/develop/README.md",
        contact = @Contact(
            name = "Henrique Ramos",
            url = "https://github.com/henriqueramoss-dev"
        ),
        license = @License(
            name = "Uso acadêmico - Projeto Senac",
            url = "https://github.com/henriqueramoss-dev/API-DE-JAVA-"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor local de desenvolvimento"
        )
    },
    externalDocs = @ExternalDocumentation(
        description = "Código-fonte e documentação do projeto",
        url = "https://github.com/henriqueramoss-dev/API-DE-JAVA-/tree/develop/biblioteca-api"
    )
)
public class OpenApiConfig {
    // A configuração é feita pelas anotações acima.
}
