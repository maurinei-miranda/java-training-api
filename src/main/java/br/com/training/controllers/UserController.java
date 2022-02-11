package br.com.training.controllers;

import br.com.training.controllers.dto.UserForm;
import br.com.training.controllers.dto.UserResponse;
import br.com.training.models.User;
import br.com.training.services.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RestControllerAdvice
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "Return a user."),
                    @ApiResponse(code = 404, message = "User not found."),
                    @ApiResponse(code = 500, message = "An exception has occurred.")
            }
    )
    @GetMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> getUser(@PathVariable String cpf) {
        User myUser = userService.findByCpf(cpf);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.OK);
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 201, message = "User created successfully. Return created user data."),
                    @ApiResponse(code = 400, message = "Invalid request. Return details of occurred exception."),
                    @ApiResponse(code = 500, message = "Some exception not mapped occurred.")
            }
    )
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserForm dto) {
        User myUser = userService.save(dto.dtoToUser());
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.CREATED);
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "User updated successfully. Return updated user data."),
                    @ApiResponse(code = 400, message = "Invalid request. Return details of occurred exception."),
                    @ApiResponse(code = 500, message = "Some exception not mapped occurred.")
            }
    )
    @PutMapping(value = "/{cpf}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String cpf, @RequestBody @Valid UserForm dto) {
        User newUser = dto.dtoToUser();
        User myUser = userService.update(cpf, newUser);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.OK);
    }

    @ApiResponses(value =
            {
                    @ApiResponse(code = 200, message = "User deleted successfully. Return deleted user data."),
                    @ApiResponse(code = 404, message = "User not found."),
                    @ApiResponse(code = 500, message = "Some exception not mapped occurred.")
            }
    )
    @DeleteMapping(value = "/{cpf}", produces = "application/json")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable String cpf) {
        User myUser = userService.findByCpf(cpf);
        userService.delete(myUser);
        return new ResponseEntity<>(UserResponse.convertToDto(myUser), HttpStatus.OK);
    }
}