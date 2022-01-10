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
    public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
        User usuario = userService.findByCpf(cpf);
        return new ResponseEntity<UserResponse>(UserResponse.convertToDto(usuario), HttpStatus.OK);
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @RequestBody User newUser) {
        User usuario = userService.updateUser(cpf, newUser);
        return new ResponseEntity<>(UserResponse.convertToDto(usuario), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String cpf) {
        User toDelete = userService.findByCpf(cpf);
        if (toDelete != null) {
            userService.deleteUser(cpf);
            return new ResponseEntity<UserResponse>(UserResponse.convertToDto(toDelete), HttpStatus.OK);
        }
        return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
    }
}