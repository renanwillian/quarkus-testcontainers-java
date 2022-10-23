package com.renanwillian.qtc.repository;

import com.renanwillian.qtc.domain.Car;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarRepository implements PanacheRepository<Car> {

    public Long countByMakeModelYear(String make, String model, Integer year)  {
        return find("lower(make) = lower(?1) AND lower(model) = lower(?2) AND year = ?3", make, model, year).count();
    }
}
