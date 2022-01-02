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
	public UserResponse createUser(@RequestBody @Valid UserForm userForm) {
		return userService.createUser(userForm);

	}

	@GetMapping(value = "/{cpf}")
	public UserResponse getUser(@PathVariable String cpf) {
		return userService.getUser(cpf);
	}

	@PutMapping(value = "/{cpf}")
	public UserResponse updateUser(@PathVariable String cpf, @RequestBody UserForm userForm) {
		return userService.updateUser(cpf, userForm);
	}

	@DeleteMapping(value = "/{cpf}")
	public UserResponse deleteUser(@PathVariable String cpf) {
		return userService.deleteUser(cpf);
	}
}
