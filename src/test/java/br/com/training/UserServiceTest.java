package br.com.training;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @InjectMocks
    private UserService userService;

    @Test(expected = NoSuchElementException.class)
    public void testFindUserByCpfFail() {
        User user = new User();
        userService.findByCpf(user.getCpf());
        verify(userRepository, times(1)).findByCpf(user.getCpf());
    }

    User defaultUser = new User("Robert Greene", "user@gmail.com", "23327434050", LocalDate.parse("1994-03-31"));
    User updatedData = new User("Geralt The Rivia", "geralt@gmail.com", "86349822030", LocalDate.parse("1168-05-05"));

    @Test
    public void testFindUserByCpfSuccess() {
        User user = defaultUser;
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        userService.findByCpf(user.getCpf());
        verify(userRepository, times(1)).findByCpf(user.getCpf());

    }

    @Test
    public void testSaveSuccess() {
        User user = defaultUser;
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserSuccess() {
        User user = defaultUser;
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        userService.delete(user);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        User user = defaultUser;
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            userService.delete(user);
        });
        verify(userRepository, times(1)).findByCpf(user.getCpf());
        assertEquals("cpf not found: " + user.getCpf(), exception.getMessage());
    }

    @Test
    public void testUpdateUserSuccess() {
        User user = defaultUser;
        User updated = updatedData;
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        userService.update(user.getCpf(), updated);
        verify(userRepository, times(1)).save(updated);
    }

    @Test
    public void testUpdateUserNotFound() {
        User user = defaultUser;
        User updated = updatedData;
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            userService.update(user.getCpf(), updated);
        });
        verify(userRepository, times(1)).findByCpf(user.getCpf());
        assertEquals("cpf not found: " + user.getCpf(), exception.getMessage());
    }


}
