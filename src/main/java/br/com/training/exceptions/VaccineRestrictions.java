package br.com.training.exceptions;

public class VaccineRestrictions extends RuntimeException {
    public VaccineRestrictions(String message){
        super(message);
    }
}
