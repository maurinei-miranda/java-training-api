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

    List<UserResponse> userListToUserResponse(List<User> userList);

    Vaccine vaccineFormToVaccine(VaccineForm vaccineForm);

    VaccineResponse vaccineToVaccineResponse(Vaccine vaccine);

    List<VaccineResponse> vaccineListToVaccineResponseList(List<Vaccine> vaccineList);

    ApplyVaccineResponse applyVaccineToApplyResponse(ApplyVaccine applyVaccine);

    List<ApplyVaccineResponse> applyVaccineListToResponseList(List<ApplyVaccine> applyVaccinesList);
}
