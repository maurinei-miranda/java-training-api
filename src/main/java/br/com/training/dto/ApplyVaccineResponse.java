package br.com.training.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ApplyVaccineResponse {

    @ApiModelProperty(value = "User's CPF will be vaccinated.")
    @NotBlank
    private String userCpf;
    @ApiModelProperty(value = "Vaccine's name will be applied;")
    @NotBlank
    private String vaccineName;
    @ApiModelProperty(value = "Date of vaccine application.")
    @NotBlank
    private LocalDate date;

    public ApplyVaccineResponse(String userCpf, String vaccineName, LocalDate date) {
        this.userCpf = userCpf;
        this.vaccineName = vaccineName;
        this.date = date;
    }

    public String getUserCpf() {
        return userCpf;
    }

    public void setUserCpf(String userCpf) {
        this.userCpf = userCpf;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
