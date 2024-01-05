package com.example.MPRprojekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {
    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }
    @GetMapping("/cars")
    public ResponseEntity <List<Car>> getCars() {
        List<Car> cars=service.getCars();
        if(!cars.isEmpty()){
            return ResponseEntity.ok(cars);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping("/cars")
    public ResponseEntity<Void> saveCar(@RequestBody Car car){
        this.service.saveCar(car);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/cars/{model}")
    public ResponseEntity <Car> findCarByModel(@PathVariable("model")String model){
        Car car=service.getCarByModel(model);
        if(car!=null) {
            return ResponseEntity.ok(car);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/cars/{index}")
    public ResponseEntity<Void> deleteCar(@PathVariable int index){
        try{
            service.deleteCars(index);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/cars")
    public ResponseEntity<Void> updateCar(@RequestBody Car car){
        try{
            service.updateCars(car);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/cars/filterByBrand")
    public ResponseEntity<List<Car>> filterByBrand(@RequestParam("name") String name){
        List<Car> cars=service.filterByBrand(name);
        if(!cars.isEmpty()){
            return ResponseEntity.ok(cars);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
