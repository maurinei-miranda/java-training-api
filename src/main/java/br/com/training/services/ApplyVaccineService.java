package br.com.training.services;


import br.com.training.models.ApplyVaccine;
import br.com.training.repositorys.ApplyVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplyVaccineService {

    @Autowired
    private ApplyVaccineRepository applyVaccineRepository;

    public List<ApplyVaccine> findAll() {
        List<ApplyVaccine> myList = new ArrayList<ApplyVaccine>();

        ApplyVaccine appVaccine = new ApplyVaccine();
        appVaccine.setId(1L);
        appVaccine.setVaccineId(12L);
        appVaccine.setUserId(23L);

        LocalDate date = LocalDate.of(1994, 3, 31);
        appVaccine.setDate(date);
        myList.add(appVaccine);

        return myList;
        //return applyVaccineRepository.findAll();
    }
}
