package br.com.training;

import br.com.training.dto.ApplyVaccineForm;
import br.com.training.exceptions.VaccineRestrictions;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
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
    public void getApplyVaccine_ByUserCpf() {
        LocalDate localDate = LocalDate.parse("2022-03-28");

        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        user.setId(1L);

        when(userService.findByCpf(user.getCpf())).thenReturn(user);
        applyVaccineService.findByUserCpf(user.getCpf());
        verify(applyVaccineRepository, times(1)).findAllByUserId(user.getId());
    }

    @Test
    public void getAllApplyVaccines() {
        applyVaccineService.findAll();
        verify(applyVaccineRepository, times(1)).findAll();
    }

    @Test
    public void saveApplyVaccine() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        Long id = 1L;
        user.setId(id);

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
        applyVaccine.setId(id);
        applyVaccine.setDate(localDate);

        applyVaccineService.save(applyVaccine);
        verify(applyVaccineRepository, times(1)).save(applyVaccine);
    }

    @Test
    public void deleteApplyVaccineTest() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        Long id = 1L;
        user.setId(id);

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
        applyVaccine.setId(id);
        applyVaccine.setDate(localDate);

        applyVaccineService.delete(applyVaccine);
        verify(applyVaccineRepository, times(1)).delete(applyVaccine);
    }

    @Test
    public void buildApplyVaccineTest() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);

        ApplyVaccine applyVaccine = new ApplyVaccine();
        applyVaccine.setVaccine(vaccine);
        applyVaccine.setUser(user);
        applyVaccine.setDate(localDate);

        when(userService.findByCpf(user.getCpf())).thenReturn(user);
        when(vaccineService.findByName(vaccine.getName())).thenReturn(vaccine);
        ApplyVaccineForm applyVaccineForm = new ApplyVaccineForm(user.getCpf(), vaccine.getName(), localDate);
        ApplyVaccine resp = applyVaccineService.buildApplyVaccine(applyVaccineForm);
        assertEquals(applyVaccine.getVaccine(), resp.getVaccine());
        assertEquals(applyVaccine.getUser(), resp.getUser());
    }

    @Test
    public void buildApplyVaccine_ReturnExceptionThirtyDays() {
        LocalDate localDate = LocalDate.now();
        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        user.setId(1L);
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);

        ApplyVaccine oldApplyVaccine = new ApplyVaccine();
        oldApplyVaccine.setVaccine(vaccine);
        oldApplyVaccine.setUser(user);
        oldApplyVaccine.setDate(localDate);

        List<ApplyVaccine> responseMock = new ArrayList<>();
        responseMock.add(oldApplyVaccine);

        when(userService.findByCpf(user.getCpf())).thenReturn(user);
        when(vaccineService.findByName(vaccine.getName())).thenReturn(vaccine);
        when(applyVaccineService.findByUserCpf(user.getCpf())).thenReturn(responseMock);

        ApplyVaccineForm applyVaccineForm = new ApplyVaccineForm(user.getCpf(), vaccine.getName(), localDate);
        VaccineRestrictions ex = assertThrows(VaccineRestrictions.class, () -> applyVaccineService.buildApplyVaccine(applyVaccineForm));
        assertEquals("Usuário já recebeu uma dose dessa vacina nos ultimos 30 dias.", ex.getMessage());
    }

    @Test
    public void buildApplyVaccineTest_ReturnExceptionMaxDoses() {
        LocalDate localDate = LocalDate.parse("2022-04-11");
        User user = new User("Robert Greene", "user@gmail.com", "23327434050", localDate);
        user.setId(1L);
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                2,
                localDate,
                localDate);

        ApplyVaccine oldApplyVaccine = new ApplyVaccine();
        oldApplyVaccine.setVaccine(vaccine);
        oldApplyVaccine.setUser(user);
        oldApplyVaccine.setDate(localDate);

        List<ApplyVaccine> responseMock = new ArrayList<>();
        responseMock.add(oldApplyVaccine);
        responseMock.add(oldApplyVaccine);

        when(userService.findByCpf(user.getCpf())).thenReturn(user);
        when(vaccineService.findByName(vaccine.getName())).thenReturn(vaccine);
        when(applyVaccineService.findByUserCpf(user.getCpf())).thenReturn(responseMock);

        ApplyVaccineForm applyVaccineForm = new ApplyVaccineForm(user.getCpf(), vaccine.getName(), localDate);
        VaccineRestrictions ex = assertThrows(VaccineRestrictions.class, () -> applyVaccineService.buildApplyVaccine(applyVaccineForm));
        assertEquals("The user Robert Greene has reached the maximum number of vaccine doses.", ex.getMessage());
    }
}
