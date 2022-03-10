package br.com.training.services;

import br.com.training.exceptions.ApplicationExceptionHandler;
import br.com.training.models.User;
import br.com.training.repositorys.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService extends ApplicationExceptionHandler {

    @Autowired
    private UserRepository userRepository;

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NoSuchElementException(cpfNotFoundMessage + cpf));
    }

    public List<User> findAllUsers() {
        List<User> listUsers = userRepository.findAll();
        return listUsers;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(String cpf, @NotNull User newUser) {
        User myUser = findByCpf(cpf);
        myUser.setName(newUser.getName());
        myUser.setEmail(newUser.getEmail());
        myUser.setBirthDate(newUser.getBirthDate());
        myUser.setCpf(newUser.getCpf());
        userRepository.save(myUser);
    }

    public void delete(@NotNull User user) {
        User userToDelete = findByCpf(user.getCpf());
        userRepository.delete(userToDelete);
    }
}
