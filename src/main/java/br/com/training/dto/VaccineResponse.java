package br.com.training.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class VaccineResponse {

    @NotBlank
    private String vaccineName;

    @NotBlank
    private String diseaseName;

    @NotNull
    private int minimumAge;

    private String diseaseDescription;

    @NotNull
    private int dosesAmount;

    private LocalDate createAt;

    private LocalDate updateAt;

    public VaccineResponse(String vaccineName, String diseaseName, String diseaseDescription, int minimumAge, int dosesAmount, LocalDate createAt, LocalDate updateAt) {
        this.vaccineName = vaccineName;
        this.diseaseName = diseaseName;
        this.diseaseDescription = diseaseDescription;
        this.minimumAge = minimumAge;
        this.dosesAmount = dosesAmount;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public int getDosesAmount() {
        return dosesAmount;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }
}
