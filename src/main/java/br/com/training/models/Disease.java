package br.com.training.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Disease {
    private String name;
    private ArrayList<String> facts;

    public Disease() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getFacts() {
        return facts;
    }

    public String getDiseaseDescription(){
        if (facts.size() > 0) {
            return facts.get(0);
        } else
            return "N/A";
    }

    public void setFacts(ArrayList<String> facts) {
        this.facts = facts;
    }
}
