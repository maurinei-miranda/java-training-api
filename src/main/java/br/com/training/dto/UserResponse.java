package br.com.training.dto;

import br.com.training.models.User;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

public class UserResponse {
    @ApiModelProperty(value = "User's full name")
    private String name;
    @ApiModelProperty(value = "User email")
    private String email;
    @ApiModelProperty(value = "User's Individual Registration. (CPF)")
    private String cpf;
    @ApiModelProperty(value = "User's date of birth.")
    private LocalDate birthDate;

    public UserResponse(String name, String email, String cpf, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }


}
