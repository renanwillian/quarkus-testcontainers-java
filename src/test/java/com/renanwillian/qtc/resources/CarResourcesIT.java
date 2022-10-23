package com.renanwillian.qtc.resources;


import com.renanwillian.qtc.PostgresTestLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import static org.hamcrest.Matchers.is;

@QuarkusTest
@QuarkusTestResource(PostgresTestLifecycleManager.class)
@TestHTTPEndpoint(CarResources.class)
public class CarResourcesIT {

    @Inject
    Flyway flyway;

    @BeforeEach
    void setUp() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void whenListResourceIsCalledAllCarsIsReturned() {
        RestAssured.given()
                   .when().get()
                   .then()
                   .statusCode(Status.OK.getStatusCode())
                   .body(is("[{\"id\":1,\"make\":\"Mercedes-Benz\",\"model\":\"500 SL\",\"year\":1992}]"));
    }

    @Test
    void whenCreateResourceIsCalledWithValidDataCreatedIsReturned() {
        RestAssured.given()
                   .when()
                   .contentType(ContentType.JSON)
                   .body("{\"make\": \"BMW\", \"model\": \"1 Series\", \"year\": 2013}")
                   .post()
                   .then()
                   .statusCode(Status.CREATED.getStatusCode())
                   .body("id", is(2))
                   .body("make", is("BMW"))
                   .body("model", is("1 Series"))
                   .body("year", is(2013));
    }

    @Test
    void whenCreateResourceIsCalledWithDuplicatedDataBadRequestIsReturned() {
        RestAssured.given()
                   .when()
                   .contentType(ContentType.JSON)
                   .body("{\"make\": \"Mercedes-Benz\", \"model\": \"500 SL\", \"year\": 1992}")
                   .post()
                   .then()
                   .statusCode(Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    void whenUpdateResourceIsCalledWithValidDataOkIsReturned() {
        RestAssured.given()
                   .when()
                   .contentType(ContentType.JSON)
                   .body("{\"make\": \"BMW\", \"model\": \"1 Series\", \"year\": 2013}")
                   .put("/1")
                   .then()
                   .statusCode(Status.OK.getStatusCode())
                   .body("id", is(1))
                   .body("make", is("BMW"))
                   .body("model", is("1 Series"))
                   .body("year", is(2013));
    }


    @Test
    void whenFindResourceIsCalledWithValidIdTheCarIsReturned() {
        RestAssured.given()
                   .when()
                   .get("/1")
                   .then()
                   .statusCode(Status.OK.getStatusCode())
                   .body("id", is(1))
                   .body("make", is("Mercedes-Benz"))
                   .body("model", is("500 SL"))
                   .body("year", is(1992));
    }

    @Test
    void whenFindResourceIsCalledWithInvalidIdNotFoundIsReturned() {
        RestAssured.given()
                   .when()
                   .get("/500")
                   .then()
                   .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    void whenDeleteResourceIsCalledWithValidIdNoContentIsReturned() {
        RestAssured.given()
                   .when()
                   .delete("/1")
                   .then()
                   .statusCode(Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void whenDeleteResourceIsCalledWithInvalidIdNotFoundIsReturned() {
        RestAssured.given()
                   .when()
                   .delete("/500")
                   .then()
                   .statusCode(Status.NOT_FOUND.getStatusCode());
    }
}
