package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.isDeleted = 0")
    List<Doctor> findAllNotDeleted();
}
