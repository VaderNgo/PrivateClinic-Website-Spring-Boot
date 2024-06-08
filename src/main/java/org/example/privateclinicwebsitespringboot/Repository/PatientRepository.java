package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.fullName != :fullName")
    List<Patient> findAllExcludeFullName(String fullName);
}
