package org.example.privateclinicwebsitespringboot.Service;

import jakarta.transaction.Transactional;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.example.privateclinicwebsitespringboot.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill createBillForAppointment(Appointment appointment){
        Bill bill = new Bill();
        bill.setAppointment(appointment);
        bill.setPatient(appointment.getPatient());
        bill.setDoctor(appointment.getDoctor());
        billRepository.save(bill);
        return bill;
    }

    @Transactional
    public Bill saveBill(Bill bill){
        bill.setTotalMoney(bill.calculateTotalMoney());
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills(){
        return billRepository.findAll();
    }
    public List<Bill> getAllBillsByPatientId(Long patientId){
        return billRepository.findByPatientId(patientId);
    }
    public List<Bill> getPendingBillsByDoctorId(Long doctorId){
        return billRepository.findPendingBillByDoctorId(doctorId);
    }
    public List<Bill> getAllBillsByDoctorId(Long doctorId){
        return billRepository.findByDoctorId(doctorId);
    }

    @Transactional
    public void updateTotalMoney(float totalMoney, Long billId){
        billRepository.updateTotalMoney(totalMoney, billId);
    }

    public Bill getBillById(Long id){
        return billRepository.findById(id).get();
    }

    public Bill getBillByAppointmentId(Long appointmentId){
        return billRepository.findByAppointmentId(appointmentId);
    }

    public void deleteBillById(Long id){
        billRepository.deleteById(id);
    }

    public void sendBillToUser(Long billId){
        Bill bill = getBillById(billId);
        bill.setStatus("Not Paid");
        billRepository.save(bill);
    }

    public int countBill(){
        return billRepository.findAll().size();
    }
    public float totalMoney(){
        return billRepository.totalMoney();
    }

    public void deleteAllByPatientId(Long patientId){
        billRepository.deleteByPatientId(patientId);
    }

    public int countBillByPatientId(Long patientId){
        return billRepository.countBillByPatientId(patientId);
    }

    public int countBillByDoctorId(Long doctorId){
        return billRepository.countBillByDoctorId(doctorId);
    }
}
