package com.example.test.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.example.model.Car;
import com.example.repository.CarRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@RunWith(SpringJUnit4ClassRunner.class)

public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    @Test
    public void should_find_no_cars_if_repository_is_empty() {
        Iterable<Car> Cars = repository.findAll();
        assertThat(Cars).isEmpty();
    }

    @Test
    public void should_store_a_car() {
        Car Car = repository.save(new Car("TestMake1", "TestModel1", "Test car desc", true));
        assertThat(Car).hasFieldOrPropertyWithValue("make", "TestMake1");
        assertThat(Car).hasFieldOrPropertyWithValue("model", "TestModel1");
        assertThat(Car).hasFieldOrPropertyWithValue("description", "Test car desc");
        assertThat(Car).hasFieldOrPropertyWithValue("isNew", true);
    }

    @Test
    public void should_find_all_cars() {
        Car car1 = new Car("TestMake1", "TestModel1","Car desc1", true);
        entityManager.persist(car1);
        Car car2 = new Car("TestMake2", "TestModel2", "Car desc2", false);
        entityManager.persist(car2);
        Car car3 = new Car("TestMake3", "TestModel3", "Car desc3", true);
        entityManager.persist(car3);
        Iterable<Car> Cars = repository.findAll();
        assertThat(Cars).hasSize(3).contains(car1, car2, car3);
    }

    @Test
    public void should_find_car_by_id() {
        Car Car = new Car("TestMake1", "TestModel1", "Car desc", true);
        entityManager.persist(Car);
        Car foundCar = repository.findById(Car.getId()).get();
        assertThat(foundCar).isEqualTo(Car);
    }

    @Test
    public void should_find_are_new_cars() {
        Car car1 = new Car("TestMake1", "TestModel1", "Car desc1", true);
        entityManager.persist(car1);
        Car car2 = new Car("TestMake2", "TestModel2", "Car desc2", false);
        entityManager.persist(car2);
        Car car3 = new Car("TestMake3", "TestModel3","Car desc3", true);
        entityManager.persist(car3);
        Iterable<Car> newCars = repository.findByIsNew(true);
        assertThat(newCars).hasSize(2).contains(car1, car3);
    }

    @Test
    public void should_find_cars_by_make_containing_string() {
        Car car1 = new Car("CarMake1", "TestModel1","Car desc1", true);
        entityManager.persist(car1);
        Car car2 = new Car("TestMake2", "TestModel2","Car desc2", false);
        entityManager.persist(car2);
        Car car3 = new Car("TestMake3", "TestModel3","Car desc3", true);
        entityManager.persist(car3);
        Iterable<Car> Cars = repository.findByMakeContaining("Test");
        assertThat(Cars).hasSize(2).contains(car2, car3);
    }

    @Test
    public void should_update_car_by_id() {
        Car car1 = new Car("TestMake1", "TestModel1","Car desc1", true);
        entityManager.persist(car1);

        Car savedCar = repository.findById(car1.getId()).get();
        savedCar.setDescription(savedCar.getDescription() + " updated by unit test");
        Car updatedCar = repository.save(savedCar);
        assertThat(updatedCar).isNotNull();
    }

    @Test
    public void should_delete_car_by_id() {
        Car car1 = new Car("TestMake1", "TestModel1","Car desc1", true);
        entityManager.persist(car1);
        Car car2 = new Car("TestMake2", "TestModel2","Car desc2", false);
        entityManager.persist(car2);
        Car car3 = new Car("TestMake3", "TestModel3","Car desc3", true);
        entityManager.persist(car3);
        repository.deleteById(car2.getId());
        Iterable<Car> Cars = repository.findAll();
        assertThat(Cars).hasSize(2).contains(car1, car3);
    }

    @Test
    public void should_delete_all_cars() {
        Car car1 = new Car("TestMake1", "TestModel1","Car desc1", true);
        entityManager.persist(car1);
        Car car2 = new Car("TestMake2", "TestModel2","Car desc2", false);
        entityManager.persist(car2);
        Car car3 = new Car("TestMake3", "TestModel3","Car desc3", true);
        entityManager.persist(car3);
        repository.deleteAll();
        Iterable<Car> Cars = repository.findAll();
        assertThat(Cars).isEmpty();
    }
}
