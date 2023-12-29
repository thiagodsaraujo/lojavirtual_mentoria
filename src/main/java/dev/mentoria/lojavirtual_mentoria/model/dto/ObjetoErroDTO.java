package dev.mentoria.lojavirtual_mentoria.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ObjetoErroDTO implements Serializable {

    @JsonProperty("error")
    private String error;

    @JsonProperty("code")
    private String code;

    // Construtores
    public ObjetoErroDTO() {
    }

    public ObjetoErroDTO(String error, String code) {
        this.error = error;
        this.code = code;
    }

    // Métodos getters e setters
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

