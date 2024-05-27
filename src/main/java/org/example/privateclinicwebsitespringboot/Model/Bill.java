package org.example.privateclinicwebsitespringboot.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "Bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "appoinment_id",nullable = true)
    private Appointment appoinment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = true)
    private Doctor doctor;

    @OneToMany(mappedBy="bill")
    private Set<BillDetail> billDetailSet;

    private Float totalMoney = 0.0f;

    @CreatedDate
    private LocalDate createdAt = LocalDate.now();

    public Bill(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Appointment getAppoinment() {
        return appoinment;
    }

    public void setAppoinment(Appointment appoinment) {
        this.appoinment = appoinment;
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

    public Set<BillDetail> getBillDetailSet() {
        return billDetailSet;
    }

    public void setBillDetailSet(Set<BillDetail> billDetailSet) {
        this.billDetailSet = billDetailSet;
    }

    public void addBillDetail(BillDetail billDetail){
        this.billDetailSet.add(billDetail);
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
