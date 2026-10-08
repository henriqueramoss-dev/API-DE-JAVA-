package com.senac.biblioteca.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tratamento centralizado das exceções da API.
 *
 * O @RestControllerAdvice permite tratar erros lançados pelos controllers
 * em um único lugar, evitando respostas genéricas ou códigos HTTP inadequados.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Trata erros de Bean Validation, como campos obrigatórios vazios,
     * e-mail inválido ou valores fora das regras definidas nas entidades.
     *
     * Retorna HTTP 400 - Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new LinkedHashMap<>();

        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            campos.put(erro.getField(), erro.getDefaultMessage());
        }

        Map<String, Object> resposta = criarResposta(
            HttpStatus.BAD_REQUEST,
            "Dados inválidos",
            "Um ou mais campos possuem valores inválidos."
        );

        resposta.put("campos", campos);

        return ResponseEntity.badRequest().body(resposta);
    }

    /**
     * Trata violações de integridade do banco.
     *
     * Exemplos:
     * - e-mail de usuário duplicado;
     * - ISBN de livro duplicado;
     * - tentativa de criar um segundo perfil para o mesmo usuário;
     * - exclusão de um registro que ainda está sendo utilizado por outro.
     *
     * Retorna HTTP 409 - Conflict, pois a requisição é válida,
     * mas entra em conflito com o estado atual dos dados.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> tratarIntegridade(DataIntegrityViolationException ex) {
        Map<String, Object> resposta = criarResposta(
            HttpStatus.CONFLICT,
            "Conflito de dados",
            "A operação não pôde ser concluída porque existe um dado duplicado ou um relacionamento que impede a alteração."
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
    }

    /**
     * Trata JSON inválido ou valores incompatíveis com o tipo esperado.
     *
     * Exemplo: informar um status diferente de ATIVO, DEVOLVIDO ou ATRASADO.
     *
     * Retorna HTTP 400 - Bad Request.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        Map<String, Object> resposta = criarResposta(
            HttpStatus.BAD_REQUEST,
            "JSON inválido",
            "O corpo da requisição está malformado ou contém um valor incompatível com o tipo esperado."
        );

        return ResponseEntity.badRequest().body(resposta);
    }

    /**
     * Trata parâmetros de URL com tipo inválido.
     *
     * Exemplo: informar um texto onde a API espera um número ou um enum.
     *
     * Retorna HTTP 400 - Bad Request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> tratarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        Map<String, Object> resposta = criarResposta(
            HttpStatus.BAD_REQUEST,
            "Parâmetro inválido",
            "O parâmetro '" + ex.getName() + "' possui um valor inválido."
        );

        return ResponseEntity.badRequest().body(resposta);
    }

    /**
     * Monta um formato padrão para as respostas de erro da API.
     */
    private Map<String, Object> criarResposta(HttpStatus status, String erro, String mensagem) {
        Map<String, Object> resposta = new LinkedHashMap<>();

        resposta.put("timestamp", LocalDateTime.now());
        resposta.put("status", status.value());
        resposta.put("erro", erro);
        resposta.put("mensagem", mensagem);

        return resposta;
    }
}
