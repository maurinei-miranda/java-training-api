package br.com.training.dto;


import br.com.training.models.Vaccine;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @CreationTimestamp
    private LocalDate createdAt;

    @ApiModelProperty(value = "Vaccine updated at.")
    @UpdateTimestamp
    private LocalDate updatedAt;

    public VaccineForm(String name, String diseaseName, int minimumAge, int dosesAmount, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.diseaseName = diseaseName;
        this.minimumAge = minimumAge;
        this.dosesAmount = dosesAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public int getDosesAmount() {
        return dosesAmount;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
