package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Car;
import net.javaguides.springboot.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class CarController {

	@Autowired
	private CarService carService;
	
	// display list of cars
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "brand", "asc", model);
	}
	
	@GetMapping("/aaa")
	public String aaa(Model model) {
		// create model attribute to bind form data
		Car car = new Car();
		model.addAttribute("car", car);
		return "aaa";
	}
	
	@PostMapping("/saveCar")
	public String saveCar(@ModelAttribute("car") Car car) {
		// save car to database
		carService.saveCar(car);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get car from the service
		Car car = carService.getCarById(id);
		
		// set car as a model attribute to pre-populate the form
		model.addAttribute("car", car);
		return "update_car";
	}
	
	@GetMapping("/deleteCar/{id}")
	public String deleteCar(@PathVariable (value = "id") long id) {
		
		// call delete car method 
		this.carService.deleteCarById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Car> page = carService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Car> listCars = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listCars", listCars);
		return "index";
	}
}
