package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;
}
