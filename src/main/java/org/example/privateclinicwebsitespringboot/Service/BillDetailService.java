package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.Model.Bill;
import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.example.privateclinicwebsitespringboot.Model.Medicine;
import org.example.privateclinicwebsitespringboot.Repository.BillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    public BillDetail createBillDetail(String medicineName, Integer quantity, Medicine medicine, Bill bill){
        BillDetail billDetail = new BillDetail();
        billDetail.setMedicineName(medicineName);
        billDetail.setQuantity(quantity);
        billDetail.setPrice(medicine.getPricePerUnit() * quantity);
        billDetail.setBill(bill);
        billDetailRepository.save(billDetail);
        return billDetail;
    }

    @Transactional
    public void clearAllDetailsByBillId(Long billId){
        billDetailRepository.deleteBillDetailById(billId);
    }

    public Set<BillDetail> getBillDetailByBillId(Long billId){
        return billDetailRepository.findByBillId(billId);
    }
}
