package com.renanwillian.qtc.dto;

import com.renanwillian.qtc.domain.Car;

import java.io.Serializable;
import java.util.Objects;

public class CarResponse implements Serializable {

    private static final long serialVersionUID = 2348428287037849372L;

    private Long id;
    private String make;
    private String model;
    private Integer year;

    public CarResponse(Car car) {
        this.id = car.getId();
        this.make = car.getMake();
        this.model = car.getModel();
        this.year = car.getYear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarResponse)) return false;
        CarResponse that = (CarResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(make, that.make) && Objects
                .equals(model, that.model) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, year);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
