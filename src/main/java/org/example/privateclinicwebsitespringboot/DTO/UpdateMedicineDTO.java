package org.example.privateclinicwebsitespringboot.DTO;

public class UpdateMedicineDTO {
    private Long id;
    private String name;
    private Float pricePerUnit;
    private String unit;

    private String description;

    public UpdateMedicineDTO() {
    }

    public UpdateMedicineDTO(Long id, String name, Float pricePerUnit, String unit, String description) {
        this.id = id;
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
