package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class BillDTO {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private Appointment appointment;
    private Set<BillDetail> billDetailSet = new HashSet<>();

    private Float totalMoney = 0.0f;
    private LocalDate createdAt;
    public BillDTO(){};

    public BillDTO(Bill bill){
        this.id = bill.getId();
        this.patient = bill.getPatient();
        this.doctor = bill.getDoctor();
        this.appointment = bill.getAppointment();
        this.billDetailSet = bill.getBillDetailSet();
        this.totalMoney = bill.getTotalMoney();
        this.createdAt = bill.getCreatedAt();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Set<BillDetail> getBillDetailSet() {
        return billDetailSet;
    }

    public void setBillDetailSet(Set<BillDetail> billDetailSet) {
        this.billDetailSet = billDetailSet;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

