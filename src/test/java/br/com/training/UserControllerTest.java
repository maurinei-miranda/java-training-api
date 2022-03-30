package br.com.training;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.exceptions.ApiError;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.User;
import br.com.training.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static br.com.training.exceptions.ApplicationExceptionHandler.cpfNotFoundMessage;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    final String usersUrl = "/users/";
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MapStructMapper mapStructMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName(value = "GET /users get a user list")
    public void getUsersList_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        List<User> listUsers = new ArrayList<>();
        listUsers.add(user);
        doReturn(listUsers).when(userService).findAllUsers();

        mockMvc.perform(get(usersUrl + "all")).andExpect(status().isOk()).andExpect(content().contentType("application/json"));

    }

    @Test
    @DisplayName(value = "GET /users get user by cpf")
    public void getUser_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserResponse userResponse = mapStructMapper.userToUserResponse(user);

        doReturn(user).when(userService).findByCpf(user.getCpf());
        String json = objectMapper.writeValueAsString(userResponse);
        mockMvc.perform(get(usersUrl + user.getCpf())).andExpect(status().isOk()).andExpect(content().contentType("application/json")).andExpect(content().string(json));
    }

    @Test
    @DisplayName(value = "POST /user create user success")
    public void postUser_Return201() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserResponse userResponse = mapStructMapper.userToUserResponse(user);

        doNothing().when(userService).save(user);
        String json = objectMapper.writeValueAsString(userResponse);
        mockMvc.perform(post(usersUrl).contentType("application/json").content(json)).andExpect(status().isCreated()).andExpect(content().contentType("application/json")).andExpect(content().string(json));
    }

    @Test
    @DisplayName(value = "POST /user create fail")
    public void postUserInvalid_Return500() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User invalidUserEmail = new User("Maurinei", "invalid-email", "31794150021", birthDate);
        String json = objectMapper.writeValueAsString(invalidUserEmail);

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "invalid email", System.currentTimeMillis());
        ResponseEntity<Object> response = ResponseEntity.status(500).body(apiError);
        //doThrow(toThrow).when(userService).save(invalidUserEmail);
        mockMvc.perform(post(usersUrl).contentType("application/json").content(json)).andExpect(status().isBadRequest()).andExpect(content().contentType("application/json")).andExpect(jsonPath("$.errors").value("email: must be a well-formed email address"));
    }

    @Test
    @DisplayName(value = "PUT /update user success")
    public void putUserSuccess_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserForm userForm = new UserForm("Maurinei", "newemail@gmail.com", "31794150021", birthDate);
        String json = objectMapper.writeValueAsString(userForm);
        doReturn(user).when(userService).findByCpf(user.getCpf());

        mockMvc.perform(put(usersUrl + user.getCpf()).contentType("application/json").content(json)).andExpect(status().isOk()).andExpect(content().contentType("application/json")).andExpect(content().string(json));
    }

    @Test
    @DisplayName(value = "PUT /update user fail, email invalid")
    public void putUserFail_Return500() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserForm userForm = new UserForm("Maurinei", "invalid-email", "31794150021", birthDate);
        String requestJson = objectMapper.writeValueAsString(userForm);

        doReturn(user).when(userService).findByCpf(user.getCpf());

        mockMvc.perform(put(usersUrl + user.getCpf()).contentType("application/json").content(requestJson)).andExpect(status().isBadRequest()).andExpect(content().contentType("application/json")).andExpect(jsonPath("$.errors").value("email: must be a well-formed email address"));

    }

    @Test
    @DisplayName(value = "DELETE /delete user success")
    public void deleteUserSuccess_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        doReturn(user).when(userService).findByCpf(user.getCpf());
        String responseJson = objectMapper.writeValueAsString(mapStructMapper.userToUserResponse(user));
        mockMvc.perform(delete(usersUrl + user.getCpf())).andExpect(status().isOk()).andExpect(content().string(responseJson));

    }

    @Test
    @DisplayName(value = "DELETE /delete user fail - not found")
    public void deleteUserFail_Return404() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        doThrow(new NoSuchElementException(cpfNotFoundMessage + user.getCpf())).when(userService).findByCpf(user.getCpf());
        mockMvc.perform(delete(usersUrl + user.getCpf())).andExpect(status().isNotFound()).andExpect(jsonPath("$.errors").value(cpfNotFoundMessage + user.getCpf()));

    }
}