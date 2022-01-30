package br.com.training.controller;

import br.com.training.controller.dto.UserForm;
import br.com.training.controller.dto.UserResponse;
import br.com.training.model.User;
import br.com.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
        User myUser = userService.findByCpf(cpf);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm dto) {
        User myUser = userService.save(dto.dtoToUser());
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @RequestBody @Valid UserForm dto) {
        User newUser = dto.dtoToUser();
        User myUser = userService.update(cpf, newUser);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String cpf) {
        User myUser = userService.findByCpf(cpf);
        userService.delete(myUser);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.OK);
    }
}