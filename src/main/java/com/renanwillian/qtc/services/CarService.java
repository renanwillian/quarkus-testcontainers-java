package com.renanwillian.qtc.services;

import com.renanwillian.qtc.domain.Car;
import com.renanwillian.qtc.dto.CarRequest;
import com.renanwillian.qtc.dto.CarResponse;
import com.renanwillian.qtc.repository.CarRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class CarService {

    @Inject
    CarRepository carRepository;

    public List<CarResponse> list() {
        return carRepository.listAll().stream().map(CarResponse::new).collect(Collectors.toList());
    }

    public CarResponse create(CarRequest carRequest) {
        if (carRepository.countByMakeModelYear(carRequest.getMake(), carRequest.getModel(), carRequest.getYear()) > 0) {
            throw new BadRequestException();
        }
        Car car = carRequest.toDomain();
        carRepository.persist(car);
        return new CarResponse(car);
    }

    public CarResponse find(Long id) {
        return new CarResponse(findById(id));
    }

    public CarResponse update(Long id, CarRequest carRequest) {
        Car car = findById(id);
        car.setMake(carRequest.getMake());
        car.setModel(carRequest.getModel());
        car.setYear(carRequest.getYear());
        carRepository.persist(car);
        return new CarResponse(car);
    }

    public void delete(Long id) {
        carRepository.delete(findById(id));
    }

    public Car findById(Long id) {
        return carRepository.findByIdOptional(id).orElseThrow(NotFoundException::new);
    }
}
