package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.Model.Patient;
import org.example.privateclinicwebsitespringboot.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    public Patient createPatient(SignUpDTO signUpDTO){
        Patient patient = new Patient();
        patient.setFullName(signUpDTO.getFullName());
        patient.setBirth(signUpDTO.getBirth());
        patient.setAddress(signUpDTO.getAddress());
        patientRepository.save(patient);
        return patient;
    }
}
