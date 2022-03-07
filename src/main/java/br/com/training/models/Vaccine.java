package br.com.training.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Vaccine implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String diseaseName;

    @Column
    private String diseaseDescription;

    @Column(nullable = false)
    private int minimumAge;

    @Column(nullable = false)
    private int dosesAmount;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column
    @UpdateTimestamp
    private LocalDate updatedAt;

    public Vaccine() {
    }

    public Vaccine(String name, String diseaseName, String diseaseDescription, int minimumAge, int dosesAmount, LocalDate createdAt, LocalDate updatedAt) {
        this.name = name;
        this.diseaseName = diseaseName;
        this.diseaseDescription = diseaseDescription;
        this.minimumAge = minimumAge;
        this.dosesAmount = dosesAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public long getId() {
        return id;
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

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
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
