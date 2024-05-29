package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.patient.id =:id")
    List<Appointment>findMyAppointments(@Param(value = "id") Long id);


    @Query("SELECT a FROM Appointment a WHERE (a.status = 'Pending' OR a.status = 'Accepted')")
    List<Appointment> findAppointmentsNotDenied();

}
