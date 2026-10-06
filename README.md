# API DE JAVA — Biblioteca

Projeto acadêmico de API REST para gerenciamento de uma biblioteca, desenvolvido com Spring Boot.

## Escopo atual

Este repositório contempla **somente os Requisitos Técnicos - Parte 1** do trabalho.

### Requisitos atendidos

- Spring Boot
- Java 21
- Maven
- Banco H2
- Spring Data JPA
- Mínimo de 5 entidades
- Relacionamentos One-to-One, One-to-Many e Many-to-Many
- Bean Validation
- Enum
- CRUD completo para cada entidade
- Mínimo de 5 endpoints REST por entidade
- Paginação com Pageable
- Consulta personalizada por entidade
- Status HTTP adequados
- Swagger/OpenAPI
- HATEOAS com EntityModel/PagedModel

## Domínio

A API representa uma biblioteca e será composta por:

- Usuario
- Perfil
- Autor
- Categoria
- Livro
- Emprestimo

## Relacionamentos planejados

- Usuario 1:1 Perfil
- Usuario 1:N Emprestimo
- Categoria 1:N Livro
- Livro 1:N Emprestimo
- Livro N:N Autor

## Planejamento

- [x] Estrutura inicial do projeto
- [x] Configuração H2 e JPA
- [x] Enums iniciais
- [ ] Entidades e relacionamentos
- [ ] Bean Validation
- [ ] Repositories
- [ ] Services
- [ ] Controllers e CRUD
- [ ] Paginação
- [ ] Consultas personalizadas
- [ ] Tratamento de erros e status HTTP
- [ ] Swagger/OpenAPI
- [ ] HATEOAS
- [ ] Testes finais

## Como executar

Abra a pasta `biblioteca-api` no IntelliJ e configure o **JDK 21**.

Execute:

```text
BibliotecaApplication.java
```

A aplicação utiliza:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

H2 Console:

```text
http://localhost:8080/h2-console
```
