package br.com.training.services;

import br.com.training.interfaces.DiseasesClient;
import br.com.training.models.Disease;
import br.com.training.models.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiseaseService {

    @Autowired
    private DiseasesClient client;


    public Root findAll() {
        return client.findAll();
    }

    public Optional<Disease> findByName(String name) {
        Root myRoot = findAll();
        List<Disease> diseases = myRoot.getDiseases();
        for (Disease disease : diseases) {
            if (disease.getName().equals(name)) {
                return Optional.of(disease);
            }
        }
        return Optional.empty();
    }
}
