package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.Doctor;

public class DoctorDisplayDTO {
    private Doctor doctor;
    private String email;

    public DoctorDisplayDTO() {
    }

    public DoctorDisplayDTO(Doctor doctor, String email) {
        this.doctor = doctor;
        this.email = email;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
