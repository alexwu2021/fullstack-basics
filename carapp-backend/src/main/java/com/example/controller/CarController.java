package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptionHandling.ResourceNotFoundException;
import com.example.repository.CarRepository;

@RestController
@RequestMapping("/api")
public class CarController {
	@Autowired
	CarRepository carRepository;

	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getAllCars(@RequestParam(required = false) String title) {
		List<Car> cars = new ArrayList<Car>();
		if (title == null) {
			carRepository.findAll().forEach(cars::add);
		} else {
			carRepository.findByMakeContaining(title).forEach(cars::add);
		}
		if (cars.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

	@GetMapping("/cars/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable("id") long id) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Car with id = " + id));
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	@PostMapping("/cars")
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		Car _car = carRepository
				.save(new Car(car.getMake(), car.getModel(), car.getDescription(), false));
		return new ResponseEntity<>(_car, HttpStatus.CREATED);
	}

	@PutMapping("/cars/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable("id") long id, @RequestBody Car car) {

		Car _car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Car with id = " + id));
		_car.setMake(car.getMake());
		_car.setModel(car.getModel());
		_car.setDescription(car.getDescription());
		_car.setNew(car.isNew());
		return new ResponseEntity<>(carRepository.save(_car), HttpStatus.OK);
	}

	@DeleteMapping("/cars/{id}")
	public ResponseEntity<HttpStatus> deleteCar(@PathVariable long id) {
		carRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/cars")
	public ResponseEntity<HttpStatus> deleteAllCars() {
		carRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/cars/is-new")
	public ResponseEntity<List<Car>> findByIsNew() {
		List<Car> cars = carRepository.findByIsNew(true);
		if (cars.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

}
