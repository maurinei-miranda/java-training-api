package br.com.training;

import br.com.training.controller.UserController;
import br.com.training.controller.dto.UserForm;
import br.com.training.model.User;
import br.com.training.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static br.com.training.exceptions.ApplicationExceptionHandler.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    //TODO adjust the tests to get the answer of the same type, stop using strings. !! especially the errors. !!
    //TODO add ObjectMapper, and stop work with magic strings!!

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    final static UserForm defaultUser = new UserForm("Robert Greene", "23327434050", "user@gmail.com", LocalDate.parse("1994-03-31"));
    final static UserForm invalidUser = new UserForm("", "23327434050", "user@gmail.com", LocalDate.parse("1994-03-31"));
    final static String userToUpdate = "{\"name\":\"Geralt\",\"email\":\"user@gmail.com\",\"cpf\":\"23327434050\",\"birthDate\":\"1994-03-31\"}";

    @Test
    public void testGetUserByCpfNotFound() throws Exception {
        NoSuchElementException expected = new NoSuchElementException("cpf not found: ");
        when(userService.findByCpf(defaultUser.getCpf())).thenThrow(expected);
        this.mockMvc.perform(
                        get("/users/" + defaultUser.getCpf())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string(containsString(cpfNotFoundError)));
    }

    @Test
    public void testGetUserByCpfSuccess() throws Exception {
        UserForm user = defaultUser;
        when(userService.findByCpf(user.getCpf())).thenReturn(user.dtoToUser());
        this.mockMvc.perform(
                        get("/users/" + user.getCpf())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(user.toJson()));
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        UserForm user = defaultUser;
        when(userService.save(user.dtoToUser())).thenReturn(user.dtoToUser());
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().string(user.toJson()));
    }

    @Test
    public void testCreateUserBlankName() throws Exception {
        UserForm user = invalidUser;
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(containsString("\"status\":\"BAD_REQUEST\",\"errors\":[\"name: must not be blank\"]")));
    }

    @Test
    public void testCreateUserCpfAlreadyExist() throws Exception {
        UserForm user = defaultUser;
        when(userService.save(user.dtoToUser())).thenThrow(new ConstraintViolationException(cpfAlreadyExistError, new SQLException(), cpfAlreadyExistError));
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                //TODO ADD ObjectMapper
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(content().string(containsString(cpfAlreadyExistError)));
    }

    @Test
    public void testCreateUserEmailAlreadyExist() throws Exception {
        UserForm user = defaultUser;
        when(userService.save(user.dtoToUser())).thenThrow(new ConstraintViolationException(emailErrorMessage, new SQLException(), emailErrorMessage));
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(content().string(containsString(emailErrorMessage)));
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        //TODO see the possibility of new builders;
        UserForm oldUser = defaultUser;
        User changedUser = oldUser.dtoToUser();
        changedUser.setName("Peppa Pig");
        UserForm userUpdated = new UserForm(changedUser.getName(), changedUser.getCpf(), changedUser.getEmail(), changedUser.getBirthDate());

        when(userService.findByCpf(oldUser.getCpf())).thenReturn(oldUser.dtoToUser());
        when(userService.update(oldUser.getCpf(), changedUser)).thenReturn(changedUser);
        this.mockMvc.perform(
                        put("/users/" + oldUser.getCpf())
                                .contentType("application/json")
                                .content(userUpdated.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isConflict())
                .andExpect(content().string(userUpdated.toJson()));

    }
}
