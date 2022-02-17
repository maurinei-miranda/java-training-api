package br.com.training.models;

import java.util.List;

public class Root {
    private List<Disease> diseases;

    public Root() {
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
}
