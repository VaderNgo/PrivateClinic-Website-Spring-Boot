package org.example.privateclinicwebsitespringboot.Repository;
import jakarta.transaction.Transactional;
import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.appointment.id = :appointmentId")
    Bill findByAppointmentId(@Param("appointmentId") Long appointmentId);

    @Modifying
    @Query("UPDATE Bill b SET b.totalMoney = :totalMoney WHERE b.id = :billId")
    void updateTotalMoney(@Param("totalMoney") float totalMoney, @Param("billId") Long billId);

    @Query("SELECT b FROM Bill b WHERE b.patient.id = :patientId")
    List<Bill> findByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT b FROM Bill b WHERE b.doctor.id = :doctorId")
    List<Bill> findByDoctorId(@Param("doctorId") Long doctorId);

    @Query("SELECT b FROM Bill b WHERE b.doctor.id = :doctorId AND b.status = 'Pending'")
    List<Bill> findPendingBillByDoctorId(@Param("doctorId") Long doctorId);

    @Query("select sum(b.totalMoney) from Bill b")
    float totalMoney();

    @Modifying
    @Transactional
    @Query("delete from Bill b where b.patient.id = :patientId")
    void deleteByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(*) from Bill b where b.patient.id = :patientId")
    int countBillByPatientId(@Param("patientId") Long patientId);

    @Query("SELECT COUNT(*) from Bill b where b.doctor.id = :doctorId")
    int countBillByDoctorId(@Param("doctorId") Long doctorId);
}
