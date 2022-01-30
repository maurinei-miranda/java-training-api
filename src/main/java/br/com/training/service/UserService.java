package br.com.training.service;

import br.com.training.exceptions.ApplicationExceptionHandler;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService extends ApplicationExceptionHandler {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf).orElseThrow(
                () -> new NoSuchElementException(cpfNotFoundError + cpf)
        );
    }

    public User update(String cpf, User newUser) {
        User myUser = findByCpf(cpf);
        myUser.setName(newUser.getName());
        myUser.setEmail(newUser.getEmail());
        myUser.setBirthDate(newUser.getBirthDate());
        myUser.setCpf(newUser.getCpf());
        return userRepository.save(myUser);
    }

    public User delete(User user) {
        User userToDelete = findByCpf(user.getCpf());
        userRepository.delete(userToDelete);
        return userToDelete;
    }
}
