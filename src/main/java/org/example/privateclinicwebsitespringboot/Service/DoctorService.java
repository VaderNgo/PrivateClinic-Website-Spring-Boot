package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.DoctorAccountDTO;
import org.example.privateclinicwebsitespringboot.DTO.DoctorDTO;
import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.DTO.UpdateDoctorDTO;
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
        doctor.setSpecialty(doctorAccountDTO.getSpecialty());
        doctor.setGender(doctorAccountDTO.getGender());
        doctorRepository.save(doctor);
        return doctor;
    }

    public List<Doctor> getAllDoctors(){
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).get();
    }

    public int countDoctor(){
        return doctorRepository.findAll().size();
    }

    public void updateDoctor(UpdateDoctorDTO updateDoctorDTO){
        Doctor doctor = doctorRepository.findById(updateDoctorDTO.getId()).get();
        doctor.setFullName(updateDoctorDTO.getFullName());
        doctor.setAddress(updateDoctorDTO.getAddress());
        doctor.setSpecialty(updateDoctorDTO.getSpecialty());
        doctor.setGender(updateDoctorDTO.getGender());
        doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long doctorId){
        doctorRepository.deleteById(doctorId);
    }
}
