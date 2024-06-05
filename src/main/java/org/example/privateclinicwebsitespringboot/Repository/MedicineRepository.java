package org.example.privateclinicwebsitespringboot.Repository;

import org.example.privateclinicwebsitespringboot.Model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    @Query("SELECT m FROM Medicine m WHERE m.name = :name")
    public Medicine findByName(@Param(value = "name") String name);
}
