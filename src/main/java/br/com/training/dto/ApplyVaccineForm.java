package br.com.training.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ApplyVaccineForm {

    @ApiModelProperty(value = "User's CPF will be vaccinated.")
    @NotBlank
    private String userCpf;
    @ApiModelProperty(value = "Vaccine's name will be applied;")
    @NotBlank
    private String vaccineName;
    @ApiModelProperty(value = "Date of vaccine application.")
    @NotBlank
    private LocalDate date;

    public ApplyVaccineForm(String userCpf, String vaccineName, LocalDate date) {
        this.userCpf = userCpf;
        this.vaccineName = vaccineName;
        this.date = date;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public LocalDate getDate() {
        return date;
    }
}
