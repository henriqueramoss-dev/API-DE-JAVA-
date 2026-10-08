package com.senac.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da API.
 * A anotação @SpringBootApplication inicia o Spring Boot e faz a leitura
 * automática dos controllers, services, repositories e configurações do projeto.
 */
@SpringBootApplication
public class BibliotecaApplication {

    /**
     * Ponto de entrada da aplicação Java.
     * O SpringApplication.run inicia o servidor web e todos os componentes do projeto.
     */
    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }
}
