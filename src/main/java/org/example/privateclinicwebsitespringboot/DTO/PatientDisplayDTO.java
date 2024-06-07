package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Model.Patient;

public class PatientDisplayDTO {
    private String email;
    private Patient patient;
    public PatientDisplayDTO() {
    }

    public PatientDisplayDTO(String email, Patient patient) {
        this.email = email;
        this.patient = patient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
