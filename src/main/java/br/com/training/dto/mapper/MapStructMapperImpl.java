package br.com.training.dto.mapper;

import br.com.training.dto.*;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.ApplyVaccine;
import br.com.training.models.User;
import br.com.training.models.Vaccine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public UserResponse userToUserResponse(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getBirthDate()
        );
    }

    @Override
    public User userDtoToUser(UserForm userForm) {
        if (userForm == null) {
            return null;
        }
        return new User(userForm.getName(),
                userForm.getEmail(),
                userForm.getCpf(),
                userForm.getBirthDate());
    }

    @Override
    public Vaccine vaccineFormToVaccine(VaccineForm vaccineForm) {
        return new Vaccine(
                vaccineForm.getName(),
                vaccineForm.getDiseaseName(),
                "N/A",
                vaccineForm.getMinimumAge(),
                vaccineForm.getDosesAmount(),
                vaccineForm.getCreatedAt(),
                vaccineForm.getUpdatedAt()
        );
    }

    @Override
    //TODO pending to Json
    public String userDtoToJson(UserForm userForm) {
        User user = new User(userForm.getName(), userForm.getEmail(), userForm.getCpf(), userForm.getBirthDate());
        return "";
    }

    @Override
    public VaccineResponse vaccineToVaccineResponse(Vaccine vaccine) {
        return new VaccineResponse(
                vaccine.getName(),
                vaccine.getDiseaseName(),
                vaccine.getDiseaseDescription(),
                vaccine.getMinimumAge(),
                vaccine.getDosesAmount(),
                vaccine.getCreatedAt(),
                vaccine.getUpdatedAt()
        );
    }

    @Override
    public ApplyVaccineResponse applyVaccineToApplyResponse(ApplyVaccine applyVaccine) {
        return new ApplyVaccineResponse(
                applyVaccine.getUser().getCpf(),
                applyVaccine.getVaccine().getName(),
                applyVaccine.getDate()
        );
    }

    public List<ApplyVaccineResponse> map(List<ApplyVaccine> applieds) {
        if (applieds == null) {
            return null;
        }

        List<ApplyVaccineResponse> list = new ArrayList<ApplyVaccineResponse>(applieds.size());
        for (ApplyVaccine item: applieds) {
            list.add(applyVaccineToApplyResponse(item));
        }
        return list;
    }
}
