package org.example.privateclinicwebsitespringboot.Repository;
import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

}
