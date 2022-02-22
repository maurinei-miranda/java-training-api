package br.com.training;

import br.com.training.controllers.UserController;
import br.com.training.dto.UserForm;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.User;
import br.com.training.services.UserService;
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

    @Autowired
    private MapStructMapper mapStructMapper;

    @MockBean
    private UserService userService;

    final static UserForm defaultUser = new UserForm("Robert Greene", "23327434050", "user@gmail.com", LocalDate.parse("1994-03-31"));
    final static UserForm userWithBlankName = new UserForm("", "23327434050", "user@gmail.com", LocalDate.parse("1994-03-31"));
    final static String badRequestString = "\"status\":\"BAD_REQUEST\",\"errors\":[\"name: must not be blank\"]";

    @Test
    public void testGetUserByCpfNotFound() throws Exception {
        NoSuchElementException expected = new NoSuchElementException("cpf not found: ");
        UserForm userForm = defaultUser;
        when(userService.findByCpf(userForm.getCpf())).thenThrow(expected);
        this.mockMvc.perform(
                        get("/users/" + userForm.getCpf())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string(containsString(cpfNotFoundError)));
    }

    @Test
    public void testGetUserByCpfSuccess() throws Exception {
        UserForm user = defaultUser;
        when(userService.findByCpf(user.getCpf())).thenReturn(mapStructMapper.userDtoToUser(user));
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
        when(userService.save(mapStructMapper.userDtoToUser(user))).thenReturn(mapStructMapper.userDtoToUser(user));
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
        UserForm user = userWithBlankName;
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(content().string(containsString(badRequestString)));
    }

    @Test
    public void testCreateUserCpfAlreadyExist() throws Exception {
        UserForm user = defaultUser;
        when(userService.save(mapStructMapper.userDtoToUser(user)))
                .thenThrow(new ConstraintViolationException(cpfAlreadyExistError, new SQLException(), cpfAlreadyExistError));
        this.mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(user.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(content().string(containsString(cpfAlreadyExistError)));
    }

    @Test
    public void testCreateUserEmailAlreadyExist() throws Exception {
        UserForm user = defaultUser;
        when(userService.save(mapStructMapper.userDtoToUser(user)))
                .thenThrow(new ConstraintViolationException(emailErrorMessage, new SQLException(), emailErrorMessage));
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
        User changedUser = mapStructMapper.userDtoToUser(defaultUser);
        changedUser.setName("John Galt");
        UserForm userFormUpdated = new UserForm(changedUser);
        when(userService.update(defaultUser.getCpf(), changedUser)).thenReturn(changedUser);
        this.mockMvc.perform(
                        put("/users/" + defaultUser.getCpf())
                                .contentType("application/json")
                                .content(userFormUpdated.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(userFormUpdated.toJson()));
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        User changedUser = mapStructMapper.userDtoToUser(defaultUser);
        changedUser.setName("John Galt");
        UserForm userFormUpdated = new UserForm(changedUser);
        NoSuchElementException expected = new NoSuchElementException(cpfNotFoundError + changedUser.getCpf());
        when(userService.update(defaultUser.getCpf(), changedUser)).thenThrow(expected);
        this.mockMvc.perform(
                        put("/users/" + defaultUser.getCpf())
                                .contentType("application/json")
                                .content(userFormUpdated.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(expected.getMessage())));
    }

    @Test
    public void testUpdateInvalidArgument() throws Exception {
        UserForm invalidUser = userWithBlankName;
        this.mockMvc.perform(
                        put("/users/" + invalidUser.getCpf())
                                .contentType("application/json")
                                .content(invalidUser.toJson())
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(badRequestString)));
    }

    @Test
    public void testDeleteUserSuccess() throws Exception {
        UserForm userForm = defaultUser;
        when(userService.findByCpf(userForm.getCpf())).thenReturn(mapStructMapper.userDtoToUser(userForm));
        this.mockMvc.perform(
                        delete("/users/" + userForm.getCpf())
                                .contentType("application/json")
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserNotFound() throws Exception {
        UserForm userForm = defaultUser;
        NoSuchElementException expected = new NoSuchElementException(cpfNotFoundError + userForm.getCpf());
        when(userService.findByCpf(userForm.getCpf())).thenThrow(expected);
        this.mockMvc.perform(
                        delete("/users/" + userForm.getCpf())
                                .contentType("application/json")
                )
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
