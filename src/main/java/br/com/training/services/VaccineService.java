package br.com.training.services;

import br.com.training.exceptions.ApplicationExceptionHandler;
import br.com.training.models.Disease;
import br.com.training.models.Vaccine;
import br.com.training.repositorys.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VaccineService extends ApplicationExceptionHandler {

    @Autowired
    private VaccineRepository vaccineRepository;

    @Autowired
    private DiseaseService diseaseService;

    public Vaccine findByName(String name) {
        return vaccineRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Vaccine '" + name + "' not found"));
    }

    public List<Vaccine> findAllVaccines() {
        return vaccineRepository.findAll();
    }

    public void save(Vaccine vaccine) {
        Disease disease = diseaseService.findByName(vaccine.getDiseaseName())
                .orElseThrow(() -> new NoSuchElementException("Disease '" + vaccine.getDiseaseName() + "' not found "));
        vaccine.setDiseaseName(disease.getName());
        vaccine.setDiseaseDescription(disease.getDiseaseDescription());
        vaccineRepository.save(vaccine);
    }

    public Vaccine update(String vaccineName, @NotNull Vaccine vaccine) {
        Disease disease = diseaseService.findByName(vaccine.getDiseaseName())
                .orElseThrow(() -> new NoSuchElementException("Disease '" + vaccine.getDiseaseName() + "' not found "));

        Vaccine oldVaccine = findByName(vaccineName);

        oldVaccine.setName(vaccine.getName());
        oldVaccine.setDosesAmount(vaccine.getDosesAmount());
        oldVaccine.setDiseaseName(disease.getName());
        oldVaccine.setDiseaseDescription(disease.getDiseaseDescription());
        oldVaccine.setMinimumAge(vaccine.getMinimumAge());
        vaccineRepository.save(oldVaccine);
        return oldVaccine;
    }

    public void delete(Vaccine vaccine) {
        Vaccine deleteVaccine = findByName(vaccine.getName());
        vaccineRepository.delete(deleteVaccine);
    }
}
