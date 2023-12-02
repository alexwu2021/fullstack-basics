package com.example.test.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.controller.CarController;
import com.example.model.Car;
import com.example.repository.CarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(CarController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CarControllerTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCar() throws Exception {
        Car car = new Car("Lexus", "RX350", "description", false);

        mockMvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldReturnCar() throws Exception {
        long id = 1L;
        Car car = new Car(id, "Lexus", "GX460","Lexus GX460 description", false);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));
        mockMvc.perform(get("/api/cars/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.description").value(car.getDescription()))
                .andExpect(jsonPath("$.new").value(car.isNew())).andDo(print());
    }

    @Test
    void shouldReturnNotFoundCar() throws Exception {
        long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/cars/{id}", id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @Test
    void shouldReturnListOfCars() throws Exception {
        List<Car> Cars = new ArrayList<>(
                Arrays.asList(
                        new Car("Tesla", "Model X", "Model X desc ", true),
                        new Car("Ford", "Taurus", "Ford Taurus desc", false)));

        when(carRepository.findAll()).thenReturn(Cars);
        mockMvc.perform(get("/api/cars")).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(Cars.size()))
                .andDo(print());

    }

    @Test
    void shouldReturnListOfCarsWithFilters() throws Exception {
        List<Car> Cars = new ArrayList<>(
                Arrays.asList(
                        new Car("BMW", "BMW X3", "BMW X3 description", true),
                        new Car("Tesla", "Model Y", "Model Y description", true)));
        String make = "Nissan";
        MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        paramsMap.add("make", make);
        when(carRepository.findByMakeContaining(make)).thenReturn(Cars);

        mockMvc.perform(get("/api/cars").params(paramsMap))
                .andExpect(status().is2xxSuccessful()) // 204 or 200 ?
                .andDo(print());
    }

    @Test
    void shouldReturnNoContentWhenMakeNotExistent() throws Exception {
        String make = "weird";
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("make", make);

        List<Car> Cars = Collections.emptyList();
        when(carRepository.findByMakeContaining(make)).thenReturn(Cars);
        mockMvc.perform(get("/api/cars").params(paramMap))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void shouldUpdateCar() throws Exception {
        long id = 1L;
        Car car = new Car("BMW", "X5", "BMW X5 desc", false);
        when(carRepository.findById(id)).thenReturn(Optional.of(car));


        Car updatedCar = new Car("Nissan", "Infinity", "Nissan Infinity desc", true);
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        mockMvc.perform(put("/api/cars/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value(updatedCar.getMake()))
                .andExpect(jsonPath("$.model").value(updatedCar.getModel()))
                .andExpect(jsonPath("$.description").value(updatedCar.getDescription()))
                .andExpect(jsonPath("$.new").value(updatedCar.isNew()))
                .andDo(print());
    }

    @Test
    void shouldDeleteCar() throws Exception {
        long id = 1L;
        doNothing().when(carRepository).deleteById(id);
        mockMvc.perform(delete("/api/cars/{id}", id))
                .andExpect(status().isAccepted())
                .andDo(print());
    }

    @Test
    void shouldDeleteAllCars() throws Exception {
        doNothing().when(carRepository).deleteAll();
        mockMvc.perform(delete("/api/cars"))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}
