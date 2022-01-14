package br.com.training.service;

import br.com.training.controller.dto.UserForm;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public User updateUser(String cpf, User newUser) {
        User user = userRepository.findByCpf(cpf);
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setBirthDate(newUser.getBirthDate());
        user.setCpf(newUser.getCpf());

        return userRepository.save(user);
    }

    public User deleteUser(String cpf) {
        User user = userRepository.findByCpf(cpf);
        userRepository.delete(user);
        return user;
    }
}
