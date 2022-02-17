package br.com.training.services;

import br.com.training.dto.VaccineForm;
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
                .orElseThrow(() -> new NoSuchElementException("Vaccine not found " + name));
    }

    public List<Vaccine> findAllVaccines() {
        return vaccineRepository.findAll();
    }

    public Vaccine save(VaccineForm vaccineForm) {
        Vaccine vaccine = vaccineForm.vaccineFormToVaccine();
        Disease disease = diseaseService.findByName(vaccineForm.getDiseaseName())
                .orElseThrow(() -> new NoSuchElementException("Disease '" + vaccineForm.getDiseaseName() + "' not found "));
        vaccine.setDiseaseName(disease.getName());
        vaccine.setDiseaseDescription(disease.getFacts().get(0));
        vaccineRepository.save(vaccine);
        return vaccine;
    }

    public void update(String vaccineName, @NotNull VaccineForm vaccineForm) {
        Vaccine oldVaccine = findByName(vaccineName);
        Vaccine newVaccine = vaccineForm.vaccineFormToVaccine();

        oldVaccine.setName(newVaccine.getName());
        oldVaccine.setDosesAmount(newVaccine.getDosesAmount());
        oldVaccine.setDiseaseName(newVaccine.getDiseaseName());
        oldVaccine.setDiseaseDescription(newVaccine.getDiseaseDescription());
        oldVaccine.setMinimumAge(newVaccine.getMinimumAge());

        vaccineRepository.save(oldVaccine);
    }

    public void delete(String name) {
        Vaccine vaccine = findByName(name);
        vaccineRepository.delete(vaccine);
    }

    //TODO criar metodo checkDisease()

}
