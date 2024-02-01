package com.example.MPRprojekt;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssuredControllerTest {
    private static final String URI="http://localhost:8080";

    @Test
public void testGetCarsById() {
    with()
        .body(new Car("Audi", "A4", "20000"))
        .contentType("application/json")
        .post(URI + "/cars/2");

    when()
        .get(URI + "/cars/2")
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
@Test
public void testDeleteCar() {
    String response = with()
            .body(new Car("Audi", "A4", "20000"))
            .contentType("application/json")
            .delete(URI + "/cars/1")
            .then()
            .statusCode(200)
            .log()
            .body()
            .extract()
            .asString();

    assertEquals("Usunieto samochod", response);
}
@Test
public void testUpdateCar() {
    with()
        .body(new Car("Audi", "A4", "20000"))
        .contentType("application/json")
        .post(URI + "/cars/1");
    String response = with()
        .body(new Car("Audi", "A4", "20000"))
        .contentType("application/json")
        .put(URI + "/cars/1")
        .then()
        .statusCode(200)
        .log()
        .body()
        .extract()
        .asString();

    assertEquals("Zaktualizowano samochod", response);
}
@Test
public void testGetCars() {
    when()
            .get(URI + "/cars")
            .then()
            .statusCode(200);
}
@Test
public void testGetCarsByBrand() {
    when()
            .get(URI + "/cars/filterByBrand?name=Audi")
            .then()
            .statusCode(200);
}
}
