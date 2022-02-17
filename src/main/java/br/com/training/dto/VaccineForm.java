package br.com.training.dto;


import br.com.training.models.Vaccine;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VaccineForm {
    @ApiModelProperty(value = "Name of vaccine.")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "Disease that the vaccine treats.")
    @NotBlank
    private String diseaseName;

    @ApiModelProperty(value = "Minimum age required to consume vaccine.")
    @NotNull
    private int minimumAge;

    @ApiModelProperty(value = "Doses amount required to immunization.")
    @NotNull
    private int dosesAmount;

    @ApiModelProperty(value = "Vaccine created at.")
    @NotNull
    private LocalDate createdAt;

    @ApiModelProperty(value = "Vaccine updated at.")
    private LocalDate updatedAt;

    public VaccineForm(String name, String diseaseName, int minimumAge, int dosesAmount, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.diseaseName = diseaseName;
        this.minimumAge = minimumAge;
        this.dosesAmount = dosesAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Vaccine vaccineFormToVaccine() {
        return new Vaccine(this.name, this.diseaseName, "descp", this.minimumAge, this.dosesAmount, this.createdAt, this.updatedAt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public void setMinimumAge(int minimumAge) {
        this.minimumAge = minimumAge;
    }

    public int getDosesAmount() {
        return dosesAmount;
    }

    public void setDosesAmount(int dosesAmount) {
        this.dosesAmount = dosesAmount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}
