package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Car;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CarService {
	List<Car> getAllCars();
	void saveCar(Car cars);
	Car getCarById(long id);
	void deleteCarById(long id);
	Page<Car> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
