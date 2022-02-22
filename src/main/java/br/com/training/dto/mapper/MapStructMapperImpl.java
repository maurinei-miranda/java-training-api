package br.com.training.dto.mapper;

import br.com.training.dto.UserForm;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.User;
import org.springframework.stereotype.Component;

@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public UserForm userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserForm(
                user.getName(), user.getEmail(), user.getCpf(), user.getBirthDate()
        );
    }

    @Override
    public User userDtoToUser(UserForm userForm) {
        if (userForm == null) {
            return null;
        }
        return new User(userForm.getName(), userForm.getEmail(), userForm.getCpf(), userForm.getBirthDate());
    }

    @Override
    public String userDtoToJson(UserForm userForm) {
        User user = new User(userForm.getName(), userForm.getEmail(), userForm.getCpf(), userForm.getBirthDate());
        return "";
    }
}
