package br.com.training.controllers;

import br.com.training.dto.VaccineForm;
import br.com.training.models.Vaccine;
import br.com.training.services.DiseaseService;
import br.com.training.services.VaccineService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/vaccines")
public class VaccineController {
    //TODO Change ResponseEntity Type.
    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private DiseaseService diseaseService;

    @GetMapping("/diseases")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(diseaseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/diseases/1")
    public ResponseEntity<Object> findByName() {
        return new ResponseEntity<>(diseaseService.findByName("Rabies - WHO"), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of vaccines"),
            @ApiResponse(code = 404, message = "Vaccine not found"),
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> findAllVaccines() {
        List<Vaccine> vaccinesList = vaccineService.findAllVaccines();
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a vaccine."),
            @ApiResponse(code = 404, message = "Vaccine not found.")
    })
    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<Object> findByName(@PathVariable String name) {
        Vaccine vaccine = vaccineService.findByName(name);
        return new ResponseEntity<>(vaccine, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Return created vaccine.")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> saveAVaccine(@RequestBody VaccineForm vaccineForm) {
        Vaccine vaccine = vaccineService.save(vaccineForm);
        return new ResponseEntity<>(vaccine, HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccine updated."),
            @ApiResponse(code = 404, message = "Vaccine not found.")
    })
    @PutMapping(value = "/{name}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> updateVaccine(@PathVariable String name, @RequestBody VaccineForm vaccineForm) {
        vaccineService.update(name, vaccineForm);
        return new ResponseEntity<>(vaccineForm, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<Object> deleteVaccine(@PathVariable String name) {
        Vaccine vaccine = vaccineService.findByName(name);
        vaccineService.delete(vaccine.getName());
        return new ResponseEntity<>(vaccine, HttpStatus.OK);
    }
}
