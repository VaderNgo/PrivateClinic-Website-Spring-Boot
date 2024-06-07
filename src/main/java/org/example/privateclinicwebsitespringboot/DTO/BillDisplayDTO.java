package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.example.privateclinicwebsitespringboot.Model.BillDetail;

import java.time.format.DateTimeFormatter;
import java.util.Set;

public class BillDisplayDTO {
    private Bill bill;
    private String patientEmail;
    private String doctorEmail;

    private Set<BillDetailDTO> billDetailDTOs;
    private String date;

    public BillDisplayDTO() {
    }
    public BillDisplayDTO(Bill bill, String patientEmail, String doctorEmail, Set<BillDetailDTO> billDetailDTOs) {
        this.bill = bill;
        this.patientEmail = patientEmail;
        this.doctorEmail = doctorEmail;
        this.billDetailDTOs = billDetailDTOs;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        this.date = this.bill.getAppointment().getDate().format(formatter);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public Set<BillDetailDTO> getBillDetailDTOs() {
        return billDetailDTOs;
    }

    public void setBillDetailDTOs(Set<BillDetailDTO> billDetailDTOs) {
        this.billDetailDTOs = billDetailDTOs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
