package org.example.privateclinicwebsitespringboot.DTO;

import org.example.privateclinicwebsitespringboot.Model.BillDetail;
import org.example.privateclinicwebsitespringboot.Model.Medicine;

public class BillDetailDTO {
    private BillDetail billDetail;
    private Medicine medicine;

    public BillDetailDTO() {
    }

    public BillDetailDTO(BillDetail billDetail, Medicine medicine) {
        this.billDetail = billDetail;
        this.medicine = medicine;
    }

    public BillDetail getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(BillDetail billDetail) {
        this.billDetail = billDetail;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
