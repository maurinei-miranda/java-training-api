package br.com.training.controller;

import javax.validation.Valid;

import br.com.training.controller.dto.UserForm;
import br.com.training.controller.dto.UserResponse;
import br.com.training.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import br.com.training.model.User;

@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm dto) {
        User usuario = userService.createUser(dto.userToDto());
        return new ResponseEntity<UserResponse>(UserResponse.convertToDto(usuario), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{cpf}")
    public User getUser(@PathVariable String cpf) {
        return userService.findByCpf(cpf);
    }

    @PutMapping(value = "/{cpf}")
    public User updateUser(@PathVariable String cpf, @RequestBody User newUser) {
        return userService.updateUser(cpf, newUser);
    }

    @DeleteMapping(value = "/{cpf}")
    public User deleteUser(@PathVariable String cpf) {
        return userService.deleteUser(cpf);
    }
}
