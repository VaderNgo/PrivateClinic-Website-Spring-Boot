package org.example.privateclinicwebsitespringboot.Repository;
import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
}
