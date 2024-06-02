package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DisplayAppointmentDTO {
    private Long Id;
    private String date;
    private String note;
    private String status;
    private Patient patient;
    private Doctor doctor;
    private LocalDate createdAt;

    public DisplayAppointmentDTO(Appointment appointment) {
        this.Id = appointment.getId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        this.date = appointment.getDate().format(formatter);
        this.status = appointment.getStatus();
        this.note = appointment.getNote();
        this.createdAt = appointment.getCreatedAt();
        this.patient = appointment.getPatient();
        this.doctor = appointment.getDoctor();

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
