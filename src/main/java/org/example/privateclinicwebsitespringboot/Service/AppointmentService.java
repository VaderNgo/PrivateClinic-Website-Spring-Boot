package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.AppointmentDTO;
import org.example.privateclinicwebsitespringboot.DTO.DisplayAppointmentDTO;
import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.example.privateclinicwebsitespringboot.Repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment createAppointment(AppointmentDTO appointmentDTO, MyUser myUser) {
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDTO.getDate());
        appointment.setNote(appointmentDTO.getNote());
        appointment.setPatient(myUser.getPatient());
        appointment.setDoctor(null);
        appointmentRepository.save(appointment);
        return appointment;
    }

    public String formatAppointmentDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        return localDateTime.format(displayFormatter);
    }

    public List<DisplayAppointmentDTO> getMyAppointments(MyUser myUser) {
        List<DisplayAppointmentDTO> temp = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findMyAppointments(myUser.getPatient().getId());
        for (Appointment appointment : appointments) {
            temp.add(new DisplayAppointmentDTO(appointment));
        }
        return temp;
    }


    public boolean isDateNotPicked(AppointmentDTO appointmentDTO) {
        List<Appointment> appointments = appointmentRepository.findAppointmentsNotDenied();
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(appointmentDTO.getDate())) {
                return false;
            }
        }
        return true;
    }

    public List<DisplayAppointmentDTO> getAppointmentsByStatus(String status) {
        List<DisplayAppointmentDTO> temp = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAppointmentsByStatus(status);
        for (Appointment appointment : appointments) {
            temp.add(new DisplayAppointmentDTO(appointment));
        }
        return temp;
    }

    public List<DisplayAppointmentDTO> getAppointmentsByStatusForDoctor(String status, Long doctorId){
        List<DisplayAppointmentDTO> temp = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAppointmentsByStatusForDoctor(status,doctorId);
        for (Appointment appointment : appointments) {
            temp.add(new DisplayAppointmentDTO(appointment));
        }
        return temp;
    }

    public void acceptAppointment(Long appointmentId, Doctor doctor) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setStatus("Accepted");
            appointment.get().setDoctor(doctor);
            appointmentRepository.save(appointment.get());
        }
    }

    public void denyAppointment(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setStatus("Denied");
            appointmentRepository.save(appointment.get());
        }
    }


    public List<DisplayAppointmentDTO> getDoctorTodayAppointments(Long doctorId) {
        List<DisplayAppointmentDTO> temp = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findDoctorTodayAppointments(doctorId,LocalDate.now());
        for (Appointment appointment : appointments) {
            temp.add(new DisplayAppointmentDTO(appointment));
        }
        return temp;
    }

    public Appointment setFinishAppointment(Long appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if(appointment.isPresent()){
            appointment.get().setStatus("Finished");
            appointmentRepository.save(appointment.get());
            return appointment.get();
        }else{
            System.out.println("Appointment not found");
            return null;
        }
    }

    public int countAppointment(){
        return appointmentRepository.findAll().size();
    }

    public List<DisplayAppointmentDTO> getAllAppointments() {
        List<DisplayAppointmentDTO> temp = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appointment : appointments) {
            temp.add(new DisplayAppointmentDTO(appointment));
        }
        return temp;
    }

    public void deleteAllByPatientId(Long patientId){
        appointmentRepository.deleteByPatientId(patientId);
    }
}
