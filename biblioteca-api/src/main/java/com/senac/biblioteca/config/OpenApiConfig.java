package com.senac.biblioteca.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração central da documentação Swagger/OpenAPI.
 *
 * Esta classe não cria endpoints. Ela define as informações gerais que aparecem
 * no topo do Swagger UI, como nome da API, descrição, versão, responsável,
 * termos de serviço, licença, servidor e link para a documentação externa.
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Biblioteca - Gestão de Acervo e Empréstimos",
        version = "1.0.0",
        description = """
            API REST desenvolvida para o gerenciamento de uma biblioteca.

            O sistema permite cadastrar e consultar usuários, perfis, autores,
            categorias, livros e empréstimos. A API utiliza paginação nas
            listagens, validação de dados, relacionamentos JPA, documentação
            OpenAPI/Swagger e links HATEOAS nas respostas.

            Projeto acadêmico desenvolvido em Java com Spring Boot.
            """,
        termsOfService = "https://github.com/henriqueramoss-dev/API-DE-JAVA-/blob/develop/README.md",
        contact = @Contact(
            name = "Henrique Ramos - Projeto Acadêmico",
            url = "https://github.com/henriqueramoss-dev/API-DE-JAVA-"
        ),
        license = @License(
            name = "Uso acadêmico - Projeto Senac",
            url = "https://github.com/henriqueramoss-dev/API-DE-JAVA-/blob/develop/README.md"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor local para desenvolvimento e testes"
        )
    },
    externalDocs = @ExternalDocumentation(
        description = "Repositório e documentação completa da API de Biblioteca",
        url = "https://github.com/henriqueramoss-dev/API-DE-JAVA-/tree/develop/biblioteca-api"
    )
)
public class OpenApiConfig {

    // A classe não precisa de métodos porque toda a configuração é feita pelas anotações.
}
