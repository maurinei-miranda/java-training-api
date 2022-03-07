package br.com.training.dto;

import br.com.training.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class UserForm {
    @ApiModelProperty(value = "User's full name")
    @NotBlank
    private String name;
    @ApiModelProperty(value = "User email")
    @Email
    private String email;
    @ApiModelProperty(value = "User's Individual Registration. (CPF)")
    @CPF
    private String cpf;
    @ApiModelProperty(value = "User's date of birth.")
    @NotNull
    private LocalDate birthDate;

    public UserForm(String name, String cpf, String email, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public UserForm(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.birthDate = user.getBirthDate();
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userJson;
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
