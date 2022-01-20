package br.com.training;

import br.com.training.exceptions.ApiError;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Test
    public void testFindUserByCpfSuccess() {
        User user = new User();
        user.setId(1L);
        user.setName("John Green");
        user.setCpf("60711829055");
        user.setEmail("user@gmail.com");
        user.setBirthDate(LocalDate.parse("1994-03-31"));
        when(userRepository.findByCpf(user.getCpf())).thenReturn(Optional.of(user));
        userService.findByCpf(user.getCpf());
        verify(userRepository, times(1)).findByCpf(user.getCpf());

    }

    @Test
    public void testSaveSuccess() {
        User user = new User();
        user.setName("John Green");
        user.setCpf("23327434050");
        user.setEmail("user@gmail.com");
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        user.setBirthDate(birthDate);
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }


}
