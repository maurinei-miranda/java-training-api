package br.com.training.controllers;

import br.com.training.models.ApplyVaccine;
import br.com.training.services.ApplyVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/appvac")
public class ApplyVaccineController {

    @Autowired
    private ApplyVaccineService applyVaccineService;

    @GetMapping()
    public ResponseEntity<Object> findAll() {
        List<ApplyVaccine> appliedList = applyVaccineService.findAll();
        return new ResponseEntity<>(appliedList, HttpStatus.OK);
    }
}
