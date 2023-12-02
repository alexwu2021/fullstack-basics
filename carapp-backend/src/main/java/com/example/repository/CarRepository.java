package com.example.repository;

import java.util.List;

import com.example.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

	List<Car> findByIsNew(boolean isNew);
	List<Car> findByMakeContaining(String make);
}
