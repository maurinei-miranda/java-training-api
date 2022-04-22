package br.com.training.controllers;

import br.com.training.dto.ApplyVaccineForm;
import br.com.training.dto.ApplyVaccineResponse;
import br.com.training.interfaces.MapStructMapper;
import br.com.training.models.ApplyVaccine;
import br.com.training.services.ApplyVaccineService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return a list of applied vaccines."),
            @ApiResponse(code = 500, message = "An exception has occurred.")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ApplyVaccineResponse>> findAll() {
        List<ApplyVaccine> list = applyVaccineService.findAll();
        List<ApplyVaccineResponse> applyVaccineResponseList = mapStructMapper.applyVaccineListToResponseList(list);
        return new ResponseEntity<>(applyVaccineResponseList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return an applied vaccine of a valid user."),
            @ApiResponse(code = 400, message = "Invalid request. Return details of occurred exception."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ApplyVaccineResponse>> findByUserCpf(@RequestParam String cpf) {
        List<ApplyVaccine> list = applyVaccineService.findByUserCpf(cpf);
        List<ApplyVaccineResponse> applyVaccineResponseList = mapStructMapper.applyVaccineListToResponseList(list);
        return new ResponseEntity<>(applyVaccineResponseList, HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Apply a vaccine and return the application values."),
            @ApiResponse(code = 400, message = "Invalid request. Return details of the occurred exception."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApplyVaccineResponse> save(@RequestBody ApplyVaccineForm applyVaccineForm) {
        ApplyVaccine applyVaccine = applyVaccineService.buildApplyVaccine(applyVaccineForm);
        applyVaccineService.save(applyVaccine);
        return new ResponseEntity<>(mapStructMapper.applyVaccineToApplyResponse(applyVaccine), HttpStatus.CREATED);
    }

    //TODO implement update applyVaccine
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Vaccine application successfully updated. Returns application data."),
            @ApiResponse(code = 400, message = "Invalid request. Return details of occurred exception."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    public ResponseEntity<ApplyVaccineResponse> update(@RequestBody ApplyVaccineForm applyVaccineForm) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User deleted successfully. Return deleted user data."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Some unmapped exception occurred.")
    })
    @DeleteMapping
    public ResponseEntity<ApplyVaccineResponse> delete(@RequestParam String cpf) {
        List<ApplyVaccine> applyVaccineList = applyVaccineService.findByUserCpf(cpf);
        if (applyVaccineList.size() <= 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        applyVaccineService.delete(applyVaccineList.get(applyVaccineList.size() - 1));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
