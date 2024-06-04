package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.example.privateclinicwebsitespringboot.Repository.BillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    public void addBillDetails(Set<BillDetail> billDetailSet){
        for(BillDetail billDetail: billDetailSet){
            billDetailRepository.save(billDetail);
        }
    }
}
