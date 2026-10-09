package com.senac.biblioteca.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Dados recebidos para criar ou atualizar um perfil.
 * O usuarioId referencia um usuário que já existe no banco.
 */
public class PerfilRequest {

    @NotBlank(message = "O telefone é obrigatório")
    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Schema(description = "Telefone do usuário", example = "11999999999")
    private String telefone;

    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 200, message = "O endereço deve ter no máximo 200 caracteres")
    @Schema(description = "Endereço do usuário", example = "Rua Exemplo, 100")
    private String endereco;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Schema(description = "Data de nascimento", example = "2000-05-10")
    private LocalDate dataNascimento;

    @NotNull(message = "O ID do usuário é obrigatório")
    @Schema(description = "ID de um usuário já cadastrado", example = "1")
    private Long usuarioId;

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
