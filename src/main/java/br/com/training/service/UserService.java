package br.com.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import br.com.training.controller.dto.UserForm;
import br.com.training.controller.dto.UserResponse;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserResponse createUser(@RequestBody @Valid UserForm userForm) {
		User newUser = userForm.dtoToUser();
		userRepository.save(newUser);
		return UserResponse.convertToDto(newUser);
	}

	public UserResponse getUser(String cpf) {
		User user = userRepository.findByCpf(cpf);
		return UserResponse.convertToDto(user);
	}

	public UserResponse updateUser(String cpf, UserForm userForm) {
		User newUser = userForm.dtoToUser();
		User user = userRepository.findByCpf(cpf);
		
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setBirthDate(newUser.getBirthDate());
		user.setCpf(newUser.getCpf());
		
		userRepository.save(user);
		return UserResponse.convertToDto(userRepository.findByCpf(cpf));
	}

	public UserResponse deleteUser(String cpf) {
		
		User user = userRepository.findByCpf(cpf);
		userRepository.delete(user);
		return UserResponse.convertToDto(user);
	}
}
