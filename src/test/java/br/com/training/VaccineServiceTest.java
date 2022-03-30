package br.com.training;

import br.com.training.models.Disease;
import br.com.training.models.Vaccine;
import br.com.training.repositorys.VaccineRepository;
import br.com.training.services.DiseaseService;
import br.com.training.services.VaccineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VaccineServiceTest {

    @Mock
    private VaccineRepository vaccineRepository;

    @Mock
    private DiseaseService diseaseService;

    @InjectMocks
    private VaccineService vaccineService;

    @Test
    public void testFindVaccineByNameSuccess_Return200() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);
        when(vaccineRepository.findByName(vaccine.getName())).thenReturn(Optional.of(vaccine));
        vaccineService.findByName("Pfizer");
        verify(vaccineRepository, times(1)).findByName(vaccine.getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindVaccineByName_NoSuchElement() {
        vaccineService.findByName("Pfizer");
        verify(vaccineRepository, times(1)).findByName("Pfizer");
    }

    @Test
    public void testFindAllVaccines_Success(){
        vaccineService.findAllVaccines();
        verify(vaccineRepository, times(1)).findAll();
    }

    @Test
    public void testSaveVaccine_Return201() {
        Vaccine vaccine = new Vaccine();
        Disease disease = new Disease();
        ArrayList<String> facts = new ArrayList<>();

        disease.setName("Covid-19");
        disease.setFacts(facts);
        facts.add("Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.");

        vaccine.setDiseaseName("Covid-19");

        when(diseaseService.findByName("Covid-19")).thenReturn(Optional.of(disease));
        vaccineService.save(vaccine);
        verify(vaccineRepository, times(1)).save(vaccine);

    }

    @Test(expected = NoSuchElementException.class)
    public void testSaveVaccine_DiseaseNotFound() {
        Vaccine vaccine = new Vaccine();
        Disease disease = new Disease();
        ArrayList<String> facts = new ArrayList<>();

        disease.setName("Covid-19");
        disease.setFacts(facts);
        facts.add("Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.");

        vaccine.setDiseaseName("Covid-19");

        vaccineService.save(vaccine);
    }

    @Test
    public void testUpdateVaccine_Success() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);

        Disease disease = new Disease();
        ArrayList<String> facts = new ArrayList<>();

        disease.setName("Covid-19");
        disease.setFacts(facts);
        facts.add("Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.");

        vaccine.setDiseaseName("Covid-19");

        when(diseaseService.findByName("Covid-19")).thenReturn(Optional.of(disease));
        when(vaccineRepository.findByName(vaccine.getName())).thenReturn(Optional.of(vaccine));
        vaccineService.update("Pfizer", vaccine);
        verify(vaccineRepository, times(1)).save(vaccine);
    }

    @Test
    public void testDeleteUser_Success() {
        LocalDate localDate = LocalDate.parse("2022-03-28");
        Vaccine vaccine = new Vaccine("Pfizer",
                "Covid-19",
                "Coronavirus disease (COVID-19) is an infectious disease caused by the SARS-CoV-2 virus.",
                5,
                4,
                localDate,
                localDate);
        when(vaccineRepository.findByName("Pfizer")).thenReturn(Optional.of(vaccine));
        vaccineService.delete(vaccine);
        verify(vaccineRepository, times(1)).delete(vaccine);
    }
}
