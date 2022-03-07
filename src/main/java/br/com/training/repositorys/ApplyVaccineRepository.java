package br.com.training.repositorys;


import br.com.training.models.ApplyVaccine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface ApplyVaccineRepository extends JpaRepository<ApplyVaccine, Long> {
    ArrayList<ApplyVaccine> findAll();
    List<ApplyVaccine> findAllByUserId(Long id);
}
