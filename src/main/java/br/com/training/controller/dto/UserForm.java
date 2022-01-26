package br.com.training.controller.dto;

import br.com.training.model.User;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class UserForm {

    @NotBlank
    private String name;
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotNull
    private LocalDate birthDate;

    public UserForm(String name, String cpf, String email, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User dtoToUser() {
        return new User(this.name, this.email, this.cpf, this.birthDate);
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
