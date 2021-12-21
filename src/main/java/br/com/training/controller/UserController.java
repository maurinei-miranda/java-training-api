package br.com.training.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;

@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody @Valid User user) {
		return userRepository.save(user);
	}

	@GetMapping (value = "/{cpf}")
	@ResponseStatus(HttpStatus.OK)
    public User getUser (@PathVariable String cpf){
        return userRepository.findByCpf(cpf);
    }
	
	@PutMapping (value = "/user/{cpf}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody @Valid User userUpdate, @PathVariable String cpf) {
		User usuario = userRepository.findByCpf(cpf);
		usuario.setName(userUpdate.getName());
		usuario.setEmail(userUpdate.getEmail());
		usuario.setBirthDate(userUpdate.getBirthDate());
		usuario.setCpf(userUpdate.getCpf());
		
		return userRepository.save(usuario);
	}
	
	@DeleteMapping (value = "/user/{cpf}")
	public void delUser(@PathVariable String cpf) {
		User usuario = userRepository.findByCpf(cpf);
		userRepository.deleteById(usuario.getId());
		
	}
	
}
