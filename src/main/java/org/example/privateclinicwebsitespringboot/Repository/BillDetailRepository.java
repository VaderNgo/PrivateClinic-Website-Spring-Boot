package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    @Query("SELECT bd FROM BillDetail bd WHERE bd.bill.id = :billId")
    Set<BillDetail> findByBillId(@Param(value = "billId") Long billId);

    @Modifying
    @Query("DELETE FROM BillDetail bd WHERE bd.bill.id = :billId")
    void deleteBillDetailById(@Param(value = "billId") Long billId);

    @Query("SELECT bd FROM BillDetail bd WHERE bd.bill.patient.id = :patientId")
    Set<BillDetail> findByPatientId(@Param(value = "patientId") Long patientId);
}
