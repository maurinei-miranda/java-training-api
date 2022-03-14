package br.com.training;

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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private ObjectMapper objectMapper;

    final String usersUrl = "/users/";

//    @Test
//    public void contextLoads() throws Exception {
//        System.out.println(userController);
//        assertThat(userController).isNotNull();
//    }


    @Test
    @DisplayName(value = "GET /users get a user")
    public void getUsersList_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        List<User> listUsers = new ArrayList<>();
        listUsers.add(user);
        doReturn(listUsers).when(userService).findAllUsers();

        mockMvc.perform(get(usersUrl + "all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
        ;

    }

    @Test
    @DisplayName(value = "GET /users get user by cpf")
    public void getUser_Return200() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserResponse userResponse = mapStructMapper.userToUserResponse(user);

        doReturn(user).when(userService).findByCpf(user.getCpf());
        String json = objectMapper.writeValueAsString(userResponse);
        mockMvc.perform(get(usersUrl + user.getCpf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(json));
    }

    @Test
    @DisplayName(value = "POST /user create user success")
    public void postUser_Return201() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User user = new User("Maurinei", "maurinei.develop@gmail.com", "31794150021", birthDate);
        UserResponse userResponse = mapStructMapper.userToUserResponse(user);

        doNothing().when(userService).save(user);
        String json = objectMapper.writeValueAsString(userResponse);
        mockMvc.perform(post(usersUrl)
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(json));
    }

    @Test
    @DisplayName(value = "POST /user create fail")
    public void postUserInvalid_Return500() throws Exception {
        LocalDate birthDate = LocalDate.parse("1994-03-31");
        User invalidUserEmail = new User("Maurinei", "maurinei-invalido", "31794150021", birthDate);
        String json = objectMapper.writeValueAsString(invalidUserEmail);

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "invalid email", System.currentTimeMillis());
        ResponseEntity<Object> response = ResponseEntity.status(500).body(apiError);
        //doThrow(toThrow).when(userService).save(invalidUserEmail);
        mockMvc.perform(post(usersUrl)
                        .contentType("application/json")
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.errors").value("email: must be a well-formed email address"));
    }
}