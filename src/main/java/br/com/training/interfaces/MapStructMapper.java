package br.com.training.interfaces;

import br.com.training.dto.*;
import br.com.training.models.ApplyVaccine;
import br.com.training.models.User;
import br.com.training.models.Vaccine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    UserResponse userToUserResponse(User user);

    User userDtoToUser(UserForm userForm);

    Vaccine vaccineFormToVaccine(VaccineForm vaccineForm);

    //TODO implement
    String userDtoToJson(UserForm userForm);

    VaccineResponse vaccineToVaccineResponse(Vaccine vaccine);

    ApplyVaccineResponse applyVaccineToApplyResponse(ApplyVaccine applyVaccine);

    List<UserResponse> userListToUserResponse(List<User> userList);

    List<ApplyVaccineResponse> map(List<ApplyVaccine> applyVaccinesList);
}
