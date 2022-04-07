package br.com.training;

import br.com.training.models.ApplyVaccine;
import br.com.training.models.User;
import br.com.training.models.Vaccine;
import br.com.training.repositorys.ApplyVaccineRepository;
import br.com.training.services.ApplyVaccineService;
import br.com.training.services.UserService;
import br.com.training.services.VaccineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApplyVaccineServiceTest {

    @Mock
    private ApplyVaccineRepository applyVaccineRepository;

    @Mock
    private UserService userService;

    @Mock
    private VaccineService vaccineService;

    @InjectMocks
    private ApplyVaccineService applyVaccineService;

    @Test
    public void getAppliedVaccine_ByUserCpf_Return200() {
        LocalDate localDate = LocalDate.parse("2022-03-28");

        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        user.setId(1L);

        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);

        ApplyVaccine applyVaccine = new ApplyVaccine();
        applyVaccine.setUser(user);
        applyVaccine.setVaccine(vaccine);

        List<ApplyVaccine> applyVaccineList = new ArrayList<>();
        applyVaccineList.add(applyVaccine);

        when(userService.findByCpf(user.getCpf())).thenReturn(user);
        when(vaccineService.findByName(vaccine.getName())).thenReturn(vaccine);
        when(applyVaccineRepository.findAllByUserId(user.getId())).thenReturn(applyVaccineList);
        applyVaccineService.findByUserCpf(user.getCpf());
        verify(applyVaccineRepository, times(1)).findAllByUserId(user.getId());
    }
}
