package com.example.MPRprojekt;

import com.example.MPRprojekt.Controllers.CarController;
import com.example.MPRprojekt.Controllers.CarExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {
    private MockMvc mockMvc;
    @Mock
    private CarService carService;
    @InjectMocks
    private CarController carController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CarExceptionHandler(), carController).build();
    }

    @Test
    public void testFindCarByModelReturns200WhenCarIsPresent() throws Exception {
        Car car = new Car("BMW1", "model1", "20000");

        when(carService.getCarByModel("model1")).thenReturn(car);

        mockMvc.perform(get("/cars/model/model1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000"));
    }

    @Test
    public void testFindCarByModelReturns404WhenCarIsNotPresent() throws Exception {
        when(carService.getCarByModel("X5")).thenReturn(null);

        mockMvc.perform(get("/cars/model/X5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void findCarByModelReturnsCarWhenModelExists() throws Exception {
        Car car = new Car(1L, "BMW1", "model1", "20000");

        when(carService.getCarByModel("model1")).thenReturn(car);

        mockMvc.perform(get("/cars/model/model1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000"));
    }

    @Test
    public void findCarByModelReturnsNotFoundWhenModelDoesNotExist() throws Exception {
        when(carService.getCarByModel("nonExistingModel")).thenReturn(null);

        mockMvc.perform(get("/cars/model/nonExistingModel"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCarsReturnsEmptyListWhenNoCarsExist() throws Exception {
        when(carService.getCars()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cars"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
    }


    @Test
    public void getCarByIdReturnsCarWhenCarExists() throws Exception {
        Car car = new Car(1L, "BMW1", "model1", "20000");
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("20000"));
    }

    @Test
    public void getCarByIdReturnsNotFoundWhenCarDoesNotExist() throws Exception {
        when(carService.getCarById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCarReturnsOkWhenCarExists() throws Exception {
        doNothing().when(carService).deleteCars(1);

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCarReturnsNotFoundWhenCarDoesNotExist() throws Exception {
        doNothing().when(carService).deleteCars(1);

        mockMvc.perform(delete("/cars/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void filterByBrandReturnsCarsWhenCarsExist() throws Exception {
        Car car = new Car(1L, "BMW1", "model1", "20000");
        when(carService.filterByBrand("BMW1")).thenReturn(Collections.singletonList(car));

        mockMvc.perform(get("/cars/filterByBrand?name=BMW1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand").value("BMW1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("model1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("20000"));
    }
@Test
public void addCarReturnsCreatedWhenCarIsValid() throws Exception {
    Car car = new Car(1L, "BMW1", "model1", "20000");
    doNothing().when(carService).saveCar(any(Car.class));

    mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(car)))
            .andExpect(status().isCreated());
}

@Test
public void addCarReturnsBadRequestWhenCarIsInvalid() throws Exception {
    Car car = new Car(1L, "", "", "");

    mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(car)))
            .andExpect(status().isBadRequest());
}

@Test
public void updateCarReturnsOkWhenCarExists() throws Exception {
    Car car = new Car(1L, "BMW1", "model1", "20000");
    doNothing().when(carService).update(any(Car.class));

    mockMvc.perform(put("/cars/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(car)))
            .andExpect(status().isOk());
}
@Test
public void addCarReturnsBadRequestWhenCarFieldsAreEmpty() throws Exception {
    Car car = new Car(1L, "", "", "");

    mockMvc.perform(post("/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(car)))
            .andExpect(status().isBadRequest());
}
@Test
public void updateCarReturnsBadRequestWhenIdIsNull() throws Exception {
    Car car = new Car(1L, "BMW1", "model1", "20000");

    mockMvc.perform(put("/cars/null")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(car)))
            .andExpect(status().isBadRequest());

}
@Test
public void deleteCarReturnsBadRequestWhenIndexIsNegative() throws Exception {
    mockMvc.perform(delete("/cars/-1"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Nieprawidlowy indeks"));
}
@Test
public void filterByBrandReturnsNoContentWhenNoCarsExist() throws Exception {
    mockMvc.perform(get("/cars/filterByBrand")
            .param("name", "NonExistentBrand"))
            .andExpect(status().isNoContent());
}
}