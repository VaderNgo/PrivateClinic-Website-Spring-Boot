package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.example.privateclinicwebsitespringboot.Repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill createBillForAppointment(Appointment appointment){
        Bill bill = new Bill();
        bill.setAppoinment(appointment);
        bill.setPatient(appointment.getPatient());
        bill.setDoctor(appointment.getDoctor());
        billRepository.save(bill);
        return bill;
    }

    public Bill getBillById(Long id){
        return billRepository.findById(id).get();
    }
}
