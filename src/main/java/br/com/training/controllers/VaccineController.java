package br.com.training.controllers;

import br.com.training.dto.VaccineForm;
import br.com.training.dto.VaccineResponse;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.Vaccine;
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
    @Autowired
    private VaccineService vaccineService;

    @Autowired
    private MapStructMapper mapStructMapper;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of vaccines"),
            @ApiResponse(code = 404, message = "Vaccine not found"),
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Vaccine>> findAllVaccines() {
        List<Vaccine> vaccinesList = vaccineService.findAllVaccines();
        return new ResponseEntity<>(vaccinesList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a vaccine."),
            @ApiResponse(code = 404, message = "Vaccine not found.")
    })
    @GetMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<VaccineResponse> findByName(@PathVariable String name) {
        Vaccine vaccine = vaccineService.findByName(name);
        return new ResponseEntity<>(mapStructMapper.vaccineToVaccineResponse(vaccine), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Return created vaccine.")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<VaccineResponse> createVaccine(@RequestBody VaccineForm vaccineForm) {
        Vaccine vaccine = mapStructMapper.vaccineFormToVaccine(vaccineForm);
        vaccineService.save(vaccine);
        return new ResponseEntity<>(mapStructMapper.vaccineToVaccineResponse(vaccine), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccine updated."),
            @ApiResponse(code = 404, message = "Vaccine not found.")
    })
    @PutMapping(value = "/{name}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<VaccineResponse> updateVaccine(@PathVariable String name, @RequestBody VaccineForm vaccineForm) {
        Vaccine vaccine = mapStructMapper.vaccineFormToVaccine(vaccineForm);
        Vaccine newVaccine = vaccineService.update(name, vaccine);
        return new ResponseEntity<>(mapStructMapper.vaccineToVaccineResponse(newVaccine), HttpStatus.OK);

    }

    @ApiResponses(
            value = {
                    @ApiResponse(code = 204, message = "Vaccine deleted")
            }
    )
    @DeleteMapping(value = "/{name}", produces = "application/json")
    public ResponseEntity<VaccineResponse> deleteVaccine(@PathVariable String name) {
        Vaccine vaccine = vaccineService.findByName(name);
        vaccineService.delete(vaccine);
        return new ResponseEntity<>(mapStructMapper.vaccineToVaccineResponse(vaccine), HttpStatus.OK);
    }
}
