package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.DTO.SignUpDTO;
import org.example.privateclinicwebsitespringboot.DTO.UpdatePatientDTO;
import org.example.privateclinicwebsitespringboot.Model.Patient;
import org.example.privateclinicwebsitespringboot.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public int countPatient(){
        return patientRepository.findAll().size();
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public void deletePatient(Long id){
        patientRepository.deleteById(id);
    }

    public void updatePatient(UpdatePatientDTO updatePatientDTO){
        Patient patient = patientRepository.findById(updatePatientDTO.getId()).get();
        patient.setFullName(updatePatientDTO.getFullName());
        patient.setAddress(updatePatientDTO.getAddress());
        patientRepository.save(patient);
    }
}
