package br.com.training.controller.dto;

import br.com.training.model.User;

import java.time.LocalDate;
import java.util.Optional;

public class UserResponse {
    private String name;
    private String email;
    private String cpf;
    private LocalDate birthDate;

    public static UserResponse convertToDto(User user) {
        return new UserResponse(user.getName(), user.getEmail(), user.getCpf(), user.getBirthDate());
    }

    public UserResponse(String name, String email, String cpf, LocalDate birthDate){
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getCpf(){
        return cpf;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }


}
