package com.renanwillian.qtc.services;

import com.renanwillian.qtc.PostgresTestLifecycleManager;
import com.renanwillian.qtc.dto.CarRequest;
import com.renanwillian.qtc.repository.CarRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@QuarkusTest
@QuarkusTestResource(PostgresTestLifecycleManager.class)
public class CarServiceIT {

    @Inject
    Flyway flyway;

    @Inject
    CarService carService;

    @Inject
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void whenListMethodIsCalledAllCarsAreReturned() {
        var list = carService.list();

        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
        assertEquals("Mercedes-Benz", list.get(0).getMake());
        assertEquals("500 SL", list.get(0).getModel());
        assertEquals(1992, list.get(0).getYear());
    }

    @Test
    void whenCreateMethodIsCalledWithValidDataCarResponseIsReturned() {
        var carRequest = new CarRequest("BMW", "1 Series", 2013);
        var carResponse = carService.create(carRequest);

        assertEquals(2, carResponse.getId());
        assertEquals("BMW", carResponse.getMake());
        assertEquals("1 Series", carResponse.getModel());
        assertEquals(2013, carResponse.getYear());
    }

    @Test
    void whenCreateMethodIsCalledWithDuplicatedDataThrowsBadRequestException() {
        var carRequest = new CarRequest("Mercedes-Benz", "500 SL", 1992);
        assertThrowsExactly(BadRequestException.class, () -> carService.create(carRequest));
    }

    @Test
    void whenUpdateMethodIsCalledWithValidDataCarResponseIsReturned() {
        var carRequest = new CarRequest( "BMW", "1 Series", 2013);
        var carResponse = carService.update(1L, carRequest);

        assertEquals(1, carResponse.getId());
        assertEquals("BMW", carResponse.getMake());
        assertEquals("1 Series", carResponse.getModel());
        assertEquals(2013, carResponse.getYear());
    }

    @Test
    void whenFindMethodIsCalledWithValidIdCarResponseIsReturned() {
        var carResponse = carService.find(1L);

        assertEquals(1, carResponse.getId());
        assertEquals("Mercedes-Benz", carResponse.getMake());
        assertEquals("500 SL", carResponse.getModel());
        assertEquals(1992, carResponse.getYear());
    }

    @Test
    void whenFindMethodIsCalledWithInvalidIdThrowsNotFoundException() {
        assertThrowsExactly(NotFoundException.class, () -> carService.find(500L));
    }

    @Test
    void whenDeleteMethodIsCalledWithValidIdTheCarIsDeleted() {
        carService.delete(1L);
        assertNull(carRepository.findById(1L));
    }

    @Test
    void whenDeleteMethodIsCalledWithInvalidIdThrowsNotFoundException() {
        assertThrowsExactly(NotFoundException.class, () -> carService.delete(500L));
    }
}
