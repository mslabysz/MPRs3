package com.example.MPRprojekt;

import com.example.MPRprojekt.Exceptions.CarIdAlreadyExistsException;
import com.example.MPRprojekt.Exceptions.CarNotFoundException;
import com.example.MPRprojekt.Exceptions.InvalidCarDataException;
import com.example.MPRprojekt.Exceptions.InvalidCarIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CarService {
    CarRepository repository;
    @Autowired
    public CarService(CarRepository repository){
        this.repository = repository;
    }
    public Car getCarByModel(String model) {
        return this.repository.findByModel(model);
    }
    public Optional<Car> getCarById(Long id) {
        Optional<Car> car = repository.findById(id);

        if (car.isPresent()) {
            return car;
        } else {
            throw new InvalidCarIdException("Samochod o id " + id + " nie istnieje.");
        }
    }
    public void saveCar(Car car) {
        if (car.getId()!=null&&repository.existsById(car.getId())) {
            throw new CarIdAlreadyExistsException("Samochód o identyfikatorze " + car.getId() + " już istnieje.");
        }
        if(car.getBrand().isEmpty() || car.getModel().isEmpty() || car.getPrice().isEmpty()){
            throw new InvalidCarDataException("Nie można dodać samochodu o pustych polach.");
        }
        repository.save(car);
    }
    public List<Car> getCars(){
        return (List<Car>) repository.findAll();
    }
    public void deleteCars(int index) {
        if (index < 0 || !repository.existsById((long) index)) {
            throw new InvalidCarIdException("Nie można usunąć samochodu o indeksie " + index + ", ponieważ indeks jest nieprawidłowy lub samochód nie istnieje.");
        }
        repository.deleteById((long) index);
    }
    public void update(Car car) {
        if (car.getId()==null || !repository.existsById(car.getId())) {
            throw new InvalidCarDataException("Nie można zaktualizować samochodu o identyfikatorze " + car.getId() + ", ponieważ nie istnieje.");
        }
        repository.save(car);
    }
    public List<Car> filterByBrand(String name){
        List<Car> allCars = (List<Car>) repository.findAll();
        List<Car> filteredCars = new ArrayList<>();

        for (Car car : allCars) {
            if (car.getBrand().toUpperCase().equals(name.toUpperCase())) {
                filteredCars.add(car);
            }
        }
        return filteredCars;
    }
}
