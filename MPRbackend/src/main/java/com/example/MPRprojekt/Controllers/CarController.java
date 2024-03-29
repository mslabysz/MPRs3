package com.example.MPRprojekt.Controllers;

import com.example.MPRprojekt.Car;
import com.example.MPRprojekt.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {
    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
        service.saveCar(new Car(1L,"Audi","A4","100000"));
        service.saveCar(new Car(2L,"BMW","X5","200000"));
        service.saveCar(new Car(3L,"Mercedes","C","300000"));
    }
    @GetMapping("/cars")
    public ResponseEntity <List<Car>> getCars() {
       return ResponseEntity.ok(service.getCars());
    }
    @GetMapping("/cars/model/{model}")
    public ResponseEntity <Car> findCarByModel(@PathVariable("model")String model){
        if(service.getCarByModel(model)==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getCarByModel(model));
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
    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody Car car){
        if(car.getBrand().isEmpty() || car.getModel().isEmpty() || car.getPrice().isEmpty()){
            return ResponseEntity.badRequest().body("Nie mozna dodac samochodu o pustych polach");
        }
        service.saveCar(car);
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano nowy samochod");
    }
    @GetMapping("/cars/{id}")
    public ResponseEntity <Optional<Car>> getCarById(@PathVariable("id") int id) {
        if(!service.getCarById((long) id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.getCarById((long) id));
    }

    @DeleteMapping("/cars/{index}")
    public ResponseEntity<String> deleteCar(@PathVariable int index){
        if(index<0){
            return ResponseEntity.badRequest().body("Nieprawidlowy indeks");
        }
        service.deleteCars(index);
        return ResponseEntity.ok("Usunieto samochod");
    }
    @PutMapping("/cars/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Car car){
        if(id==null){
            return ResponseEntity.badRequest().body("Nieprawidlowy index");
        }
        car.setId(id);
        service.update(car);
        return ResponseEntity.ok("Zaktualizowano samochod");
    }
}
