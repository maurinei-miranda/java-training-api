package br.com.training.services;


import br.com.training.dto.ApplyVaccineForm;
import br.com.training.exceptions.ApplicationExceptionHandler;
import br.com.training.exceptions.VaccineRestrictions;
import br.com.training.models.ApplyVaccine;
import br.com.training.models.User;
import br.com.training.models.Vaccine;
import br.com.training.repositorys.ApplyVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplyVaccineService extends ApplicationExceptionHandler {

    @Autowired
    private ApplyVaccineRepository applyVaccineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VaccineService vaccineService;

    public List<ApplyVaccine> findByUserCpf(String cpf) {
        User user = userService.findByCpf(cpf);
        return applyVaccineRepository.findAllByUserId(user.getId());
    }

    public List<ApplyVaccine> findAll() {
        return applyVaccineRepository.findAll();
    }

    public void save(ApplyVaccine applyVaccine) {
        applyVaccineRepository.save(applyVaccine);
    }

    public void delete(ApplyVaccine applyVaccine) {
        applyVaccineRepository.delete(applyVaccine);
    }

    // Constructor method
    public ApplyVaccine defineApplyVaccine(ApplyVaccineForm applyVaccineForm) {
        ApplyVaccine applyVaccine = new ApplyVaccine();

        User user = userService.findByCpf(applyVaccineForm.getUserCpf());
        Vaccine vaccine = vaccineService.findByName(applyVaccineForm.getVaccineName());

        applyVaccine.setUser(user);
        applyVaccine.setVaccine(vaccine);

        applyVaccine.setDate(applyVaccineForm.getDate());

        checkDosesAmount(user.getCpf(), vaccine);
        checkVaccineLastDays(user.getCpf(), applyVaccine);

        return applyVaccine;
    }

    private void checkVaccineLastDays(String cpf, ApplyVaccine applyVaccine) {
        List<ApplyVaccine> list = findByUserCpf(cpf).stream()
                .filter(c -> c.getVaccine().getName().equals(applyVaccine.getVaccine().getName()))
                .collect(Collectors.toList());

        if (list.size() != 0) {
            ApplyVaccine lastItem = list.get(list.size() - 1);

            LocalDate primeiraDt = lastItem.getDate();
            LocalDate segundaDt = applyVaccine.getDate();

            Period periodo = Period.between(primeiraDt, segundaDt);
            int dias = periodo.getDays();
            int totalDias = (periodo.getMonths() * 30) + dias;
            if (totalDias <= 30) {
                throw new VaccineRestrictions("Usuário já recebeu uma dose dessa vacina nos ultimos 30 dias.");
            }
        }
    }

    private void checkDosesAmount(String cpf, Vaccine vaccine) {
        List<ApplyVaccine> list = findByUserCpf(cpf);
        long count = list.stream()
                .filter(c -> c.getVaccine().getName().equals(vaccine.getName()))
                .count();
        if (count >= vaccine.getDosesAmount()) {
            throw new VaccineRestrictions("The user " + list.get(0).getUser().getName() + " has reached the maximum number of vaccine doses.");
        }
    }


//    public Optional<ApplyVaccine> findById(Long id){
//        return applyVaccineRepository.findById(id);
//    }
//
//    public void deleteAppliedVaccine(Long id){
//        Optional<ApplyVaccine> appliedVaccine = findById(id);
//        applyVaccineRepository.delete(appliedVaccine.get());
//
//    }
}
