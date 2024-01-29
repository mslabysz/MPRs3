package com.example.MPRprojekt.Controllers;

import org.springframework.ui.Model;
import com.example.MPRprojekt.Car;
import com.example.MPRprojekt.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebController {
    private final CarService service;
    @Autowired
    public WebController(CarService service) {
        this.service = service;
    }
    @GetMapping(value = "/welcome")
    public String getWelcomeView(){
        return "welcome";
    }
    @GetMapping(value = "/index")
    public String getIndexView(Model model){
        List<Car> cars = service.getCars();
        model.addAttribute("cars", cars);
        return "index";
    }
    @GetMapping(value = "/addCar")
    public String getAddCarView(Model model){
        model.addAttribute("car",new Car(0L,"","",""));
        return "addCar";
    }
    @PostMapping(value = "/addCar")
    public String addCar(Car car, Model model, RedirectAttributes redirectAttributes){
        String errorMessage = validateCar(car);
        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);
            return "addCar";
        }
        service.saveCar(car);
        redirectAttributes.addFlashAttribute("successMessage","Dodano nowy samochod");
        return "redirect:/index";
    }
    private String validateCar(Car car){
        if(car.getBrand().isEmpty() || car.getModel().isEmpty() || car.getPrice().isEmpty()){
            return "Wszystkie pola musza byc wypelnione";
        }
        if(!car.getPrice().matches("[0-9]+")){
            return "Cena musi byc liczba";
        }
        return null;
    }
    @GetMapping(value = "/editCar/{id}")
    public String getEditCarView(@PathVariable("id") long id, Model model){
        Car car = service.getCarById(id);
        if(car!=null){
            model.addAttribute("car",car);
            return "editCar";
        }else{
            return "redirect:/index";
        }
    }
    @PostMapping(value = "/editCar")
    public String postEditCarView(Car car, Model model, RedirectAttributes redirectAttributes){
        String errorMessage = validateCar(car);
        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);
            return "editCar";
        }
        service.update(car);
        redirectAttributes.addFlashAttribute("successMessage","Zaktualizowano samochod");
        return "redirect:/index";
    }
    @GetMapping(value = "/deleteCar/{id}")
    public String deleteCar(@PathVariable("id") long id){
        service.deleteCars(id);
        return "redirect:/index";
    }
}
