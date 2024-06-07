package org.example.privateclinicwebsitespringboot.Service;

import org.example.privateclinicwebsitespringboot.Model.Medicine;
import org.example.privateclinicwebsitespringboot.Repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public void addMedicine(Medicine medicine) {
        medicineRepository.save(medicine);
    }
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }

    public Medicine getMedicineByName(String name) {
        return medicineRepository.findByName(name);
    }

    public int countMedicine(){
        return medicineRepository.findAll().size();
    }
}
