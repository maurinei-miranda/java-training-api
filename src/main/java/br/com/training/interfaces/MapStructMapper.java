package br.com.training.interfaces;

import br.com.training.dto.UserForm;
import br.com.training.models.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    UserForm userToUserDto(User user);
    User userDtoToUser(UserForm userForm);
    String userDtoToJson(UserForm userForm);
}
