package com.example.MPRprojekt;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssuredControllerTest {
    private static final String URI="http://localhost:8080";

    @Test
    public void testGetCarsById() {
        when()
                .get(URI + "/cars/1")
                .then()
                .statusCode(200);
    }
    @Test
    public void testGetCars2(){
        Car car= when()
                .get(URI + "/cars/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Car.class);
        assertEquals(1L,car.getId());
        assertEquals("Audi",car.getBrand());
    }
@Test
public void testPostCar(){
    String response = with()
            .body(new Car("Audi","A4","20000"))
            .contentType("application/json")
            .post(URI + "/cars")
            .then()
            .statusCode(201)
            .log()
            .body()
            .extract()
            .asString();

    assertEquals("Dodano nowy samochod", response);
}

}
