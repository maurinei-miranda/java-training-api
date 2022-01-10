package br.com.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(@RequestBody @Valid User user) {
        return userRepository.save(user);
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public User updateUser(String cpf, User newUser) {
        try {
            User user = userRepository.findByCpf(cpf);
        } catch (
                NullPointerException e) {
            System.out.println("User not found" + e);
        }

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
