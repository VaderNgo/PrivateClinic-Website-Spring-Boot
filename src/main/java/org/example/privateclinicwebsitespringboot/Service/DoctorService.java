package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.DoctorAccountDTO;
import org.example.privateclinicwebsitespringboot.DTO.DoctorDTO;
import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(DoctorAccountDTO doctorAccountDTO){
        Doctor doctor = new Doctor();
        doctor.setFullName(doctorAccountDTO.getFullName());
        doctor.setBirth(doctorAccountDTO.getBirth());
        doctor.setAddress(doctorAccountDTO.getAddress());
        doctorRepository.save(doctor);
        return doctor;
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).get();
    }

}
