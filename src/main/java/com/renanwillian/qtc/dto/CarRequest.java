package com.renanwillian.qtc.dto;

import com.renanwillian.qtc.domain.Car;

import java.io.Serializable;
import java.util.Objects;

public class CarRequest implements Serializable {

    private static final long serialVersionUID = 7937264281409749414L;

    private String make;
    private String model;
    private Integer year;

    public Car toDomain() {
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        return car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarRequest)) return false;
        CarRequest that = (CarRequest) o;
        return Objects.equals(make, that.make) && Objects.equals(model, that.model) && Objects
                .equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, year);
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
