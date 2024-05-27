package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
