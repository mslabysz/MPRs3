package com.example.MPRprojekt;

import com.example.MPRprojekt.Controllers.CarController;
import com.example.MPRprojekt.Controllers.CarExceptionHandler;
import com.example.MPRprojekt.Exceptions.CarIdAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class CarTest {
    @Mock
    private CarRepository repository;
    private AutoCloseable openMocks;
    private CarService carService;
    private CarController carController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        carService = new CarService(repository);
    }
    @BeforeEach
    public void setUp(){
        carController = new CarController(carService);
        this.mockMvc= MockMvcBuilders.standaloneSetup(carController)
                .setControllerAdvice(new CarExceptionHandler())
                .build();
    }
    @AfterEach
    public void tearDown() throws Exception{
        openMocks.close();
    }
    @Test
    public void findFinds(){
        String model="model";
        Car car=new Car(2L,"BMW","X5","20000");
        when(repository.findByModel(model)).thenReturn(car);
        Car result=carService.getCarByModel(model);
        assertEquals(car,result);
    }
    @Test
    public void saveSaves(){
        String model="model";
        String price="20000";
        Car car=new Car(2L,"BMW","model","20000");
        ArgumentCaptor<Car> captor=ArgumentCaptor.forClass(Car.class);
        when(repository.save(captor.capture())).thenReturn(car);
        carService.saveCar(car);
        Mockito.verify(repository, Mockito.times(1))
                .save(any());
        Car carFromSaveCall=captor.getValue();
        assertEquals(car,carFromSaveCall);
    }
    @Test
    public void testFilterByBrand() {
        Car car1 = new Car(1L,"BMW1","model1","20000");
        Car car2 = new Car(2L,"BMW2","model2","20000");
        Car car3 = new Car(3L,"BMW3","model3","20000");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(repository.findAll()).thenReturn(allCars);

        List<Car> filteredCars1 = carService.filterByBrand("BMW1");
        List<Car> filteredCars2 = carService.filterByBrand("Inny");
        List<Car> filteredCars3 = carService.filterByBrand("Model");

        assertEquals(1, filteredCars1.size());
        assertEquals(0, filteredCars2.size());
        assertEquals(0, filteredCars3.size());
    }
    @Test
    public void testGetCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(1L,"BMW1","model1","20000"));
        cars.add(new Car(2L,"BMW2","model2","20000"));
        when(repository.findAll()).thenReturn(cars);

        List<Car> result = carService.getCars();

        assertEquals(cars, result);
    }
    @Test
    public void testDeleteCar() {
        Car car=new Car(1L,"BMW1","model1","20000");
        when(repository.existsById(car.getId())).thenReturn(true);
        long carId = 1L;
        carService.deleteCars((int) carId);

        Mockito.verify(repository, Mockito.times(1)).deleteById(carId);
    }
    @Test
    public void testUpdateCar() {
        Car carToUpdate = new Car(1L,"BMW1","model1","20000");
        when(repository.existsById(carToUpdate.getId())).thenReturn(true);

        carService.updateCars(carToUpdate);

        Mockito.verify(repository, Mockito.times(1)).save(carToUpdate);
    }
    @Test
    //test negatywny
    public void testUpdateCarWhenCarDoesNotExist() {
        Car carToUpdate = new Car(1L,"BMW1","model1","20000");
        when(repository.existsById(carToUpdate.getId())).thenReturn(false);

        assertThrows(Exception.class, () -> {
            carService.updateCars(carToUpdate);
        });

        Mockito.verify(repository, Mockito.never()).save(carToUpdate);
    }
    @Test
    public void carAddThrowsExceptionWhenCarPresent(){
        Car car=new Car(1L,"BMW1","model1","20000");
        when(repository.existsById(car.getId())).thenReturn(true);
        assertThrows(CarIdAlreadyExistsException.class,()->carService.saveCar(car));
    }
    @Test
    public void testFindCarByModelReturns200WhenCarIsPresent() throws Exception {
        Car car = new Car(1L,"BMW1","model1","20000");

        when(carService.getCarByModel("model1")).thenReturn(car);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/model1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000"));
    }
    @Test
    public void testFindCarByModelReturns404WhenCarIsNotPresent() throws Exception {
        when(carService.getCarByModel("X5")).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/cars/X5"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testGetCarsReturns200WithValidJSON() throws Exception {
        Car car = new Car(1L,"BMW1","model1","20000");
        when(carService.getCars()).thenReturn(Collections.singletonList(car));

        mockMvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].price").value("20000"));
    }
}
