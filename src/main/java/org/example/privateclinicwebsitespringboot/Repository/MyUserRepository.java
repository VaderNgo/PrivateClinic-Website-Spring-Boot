package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository <MyUser,Long>{
    Optional<MyUser> findByEmail(String email);
    Optional<MyUser> findByUsernameOrEmail(String username, String email);
    Optional<MyUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT u.email FROM MyUser u WHERE u.patient.id = :patientId")
    Optional<String> findEmailByPatientId(Long patientId);

    @Query("SELECT u.email FROM MyUser u WHERE u.doctor.id = :doctorId")
    Optional<String> findEmailByDoctorId(Long doctorId);
}
