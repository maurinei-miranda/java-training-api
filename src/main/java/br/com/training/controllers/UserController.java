package br.com.training.controllers;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.User;
import br.com.training.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MapStructMapper mapStructMapper;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of users"),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<User> userList = userService.findAllUsers();
        List<UserResponse> userResponseList = mapStructMapper.userListToUserResponse(userList);
        return new ResponseEntity<>(userResponseList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a user."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @GetMapping(value = "/{cpf}", produces = "application/json")
    public ResponseEntity<UserResponse> findByCpf(@PathVariable String cpf) {
        User user = userService.findByCpf(cpf);
        return new ResponseEntity<>(mapStructMapper.userToUserResponse(user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully. Return created user data."),
            @ApiResponse(code = 400, message = "Invalid request. Return details of the occurred exception."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm userForm) {
        User user = mapStructMapper.userDtoToUser(userForm);
        userService.save(user);
        return new ResponseEntity<>(mapStructMapper.userToUserResponse(user), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User updated successfully. Return updated user data."),
            @ApiResponse(code = 400, message = "Invalid request. Return details of occurred exception."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @PutMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @RequestBody @Valid UserForm userForm) {
        User user = mapStructMapper.userDtoToUser(userForm);
        userService.update(cpf, user);
        return new ResponseEntity<>(mapStructMapper.userToUserResponse(user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully. Return deleted user data."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @DeleteMapping(value = "/{cpf}", produces = "application/json")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String cpf) {
        User user = userService.findByCpf(cpf);
        userService.delete(user);
        return new ResponseEntity<>(mapStructMapper.userToUserResponse(user), HttpStatus.OK);
    }
}