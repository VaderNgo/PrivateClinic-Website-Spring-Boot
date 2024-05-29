package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(AppointmentDTO appointmentDTO, MyUser myUser){
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDTO.getDate());
        appointment.setNote(appointmentDTO.getNote());
        //appointment.setStatus("Pending");
        appointment.setPatient(myUser.getPatient());
        appointment.setDoctor(null);
        appointmentRepository.save(appointment);
        return appointment;
    }

    public List<Appointment> getMyAppointments(MyUser myUser){
        return appointmentRepository.findMyAppointments(myUser.getPatient().getId());
    }

    public boolean isDateNotPicked(AppointmentDTO appointmentDTO){
        List<Appointment> appointments = appointmentRepository.findAppointmentsNotDenied();
        for(Appointment appointment:appointments){
            if(appointment.getDate().equals(appointmentDTO.getDate())){
                return false;
            }
        }
        return true;
    }
    
}
