package com.example.MPRprojekt;

import com.example.MPRprojekt.Exceptions.CarIdAlreadyExistsException;
import com.example.MPRprojekt.Exceptions.CarNotFoundException;
import com.example.MPRprojekt.Exceptions.InvalidCarDataException;
import com.example.MPRprojekt.Exceptions.InvalidCarIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    public static final String BASE_URL = "http://localhost:8080";
    RestClient restClient;
    public CarService(){
        restClient = RestClient.create();
    }
//    public Car getCarByModel(String model) {
//        Car car = repository.findByModel(model);
//        if (car == null) {
//            throw new CarNotFoundException("Samochód o modelu " + model + " nie istnieje.");
//        }
//        return car;
//    }
    public Car getCarById(Long id) {
        Car car = restClient
                .get()
                .uri(BASE_URL + "/cars/" + id)
                .retrieve()
                .body(Car.class);
        if (car != null) {
            return car;
        } else {
            throw new CarNotFoundException("Samochód o id " + id + " nie istnieje.");
        }
    }
    public void saveCar(Car car) {
       if(car.getBrand().isEmpty() || car.getModel().isEmpty() || car.getPrice().isEmpty()){
            throw new InvalidCarDataException("Nie można dodać samochodu o pustych polach.");
        }
        ResponseEntity<Void> response=restClient
                .post()
                .uri(BASE_URL + "/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .body(car)
                .retrieve()
                .toBodilessEntity();
    }
    public List<Car> getCars(){
        List<Car> cars = restClient
                .get()
                .uri(BASE_URL + "/cars")
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
        return cars;
    }
    public void deleteCars(Long id) {
        restClient
                .delete()
                .uri(BASE_URL + "/cars/" + id)
                .retrieve()
                .toBodilessEntity();
    }
    public void updateCars(Car car) {
        if(car.getId()==null){
            throw new InvalidCarDataException("Nie można zaktualizować samochodu o identyfikatorze " + car.getId() + ", ponieważ nie istnieje.");
        }
        restClient
                .put()
                .uri(BASE_URL + "/cars")
                .body(car)
                .retrieve()
                .toBodilessEntity();
    }
//    public List<Car> filterByBrand(String name){
//        List<Car> allCars = (List<Car>) repository.findAll();
//        List<Car> filteredCars = new ArrayList<>();
//
//        for (Car car : allCars) {
//            if (car.getBrand().toUpperCase().equals(name.toUpperCase())) {
//                filteredCars.add(car);
//            }
//        }
//        return filteredCars;
//    }
}
