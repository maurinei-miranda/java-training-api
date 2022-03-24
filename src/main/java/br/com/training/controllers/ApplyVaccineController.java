package br.com.training.controllers;

import br.com.training.dto.ApplyVaccineForm;
import br.com.training.dto.ApplyVaccineResponse;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.ApplyVaccine;
import br.com.training.services.ApplyVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/appvac")
public class ApplyVaccineController {

    @Autowired
    private ApplyVaccineService applyVaccineService;

    @Autowired
    private MapStructMapper mapStructMapper;

    @GetMapping("/all")
    public ResponseEntity<List<ApplyVaccineResponse>> findAll() {
        List<ApplyVaccine> list = applyVaccineService.findAll();
        List<ApplyVaccineResponse> myList = mapStructMapper.map(list);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ApplyVaccineResponse>> findByUserCpf(@RequestParam String cpf) {
        List<ApplyVaccine> list = applyVaccineService.findByUserCpf(cpf);
        List<ApplyVaccineResponse> myList = mapStructMapper.map(list);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApplyVaccineResponse> save(@RequestBody ApplyVaccineForm applyVaccineForm) {
        ApplyVaccine applyVaccine = applyVaccineService.defineApplyVaccine(applyVaccineForm);
        applyVaccineService.save(applyVaccine);
        return new ResponseEntity<>(mapStructMapper.applyVaccineToApplyResponse(applyVaccine), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ApplyVaccineResponse> delete(@RequestParam String cpf) {
        List<ApplyVaccine> applyVaccineList = applyVaccineService.findByUserCpf(cpf);
        if (applyVaccineList.size() <= 0) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        }
        applyVaccineService.delete(applyVaccineList.get(applyVaccineList.size()-1));
        return new ResponseEntity(HttpStatus.OK);
    }
}
