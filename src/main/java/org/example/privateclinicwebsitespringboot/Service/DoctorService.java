package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.example.privateclinicwebsitespringboot.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(SignUpDTO signUpDTO){
        Doctor doctor = new Doctor();
        doctor.setFullName(signUpDTO.getFullName());
        doctor.setBirth(signUpDTO.getBirth());
        doctor.setAddress(signUpDTO.getAddress());
        doctorRepository.save(doctor);
        return doctor;
    }
}
