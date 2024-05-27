package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.Patient;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Long Id;
    private LocalDateTime date;
    private String note;
    private String status;
    private Patient patient;
    private Doctor doctor;

    public AppointmentDTO() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
