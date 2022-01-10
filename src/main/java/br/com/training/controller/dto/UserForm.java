package br.com.training.controller.dto;

import br.com.training.model.User;

import java.time.LocalDate;


public class UserForm {

    private String name;
    private String cpf;
    private String email;
    private LocalDate birthDate;

    public UserForm(String name, String cpf, String email, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
    }

    public User userToDto() {
        return new User(name, email, cpf, birthDate);
    }
}
