package br.com.spring.ecommerce.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring.ecommerce.dto.CuponDTO;
import br.com.spring.ecommerce.model.Cupon;
import br.com.spring.ecommerce.service.CuponService;

@RestController
@RequestMapping("/cupon")
public class CuponController {

	@Autowired
	private CuponService cuponService;

	@PostMapping(value = "/insert")
	private ResponseEntity<Cupon> insertCupon(@RequestBody @Valid CuponDTO dto) throws ParseException {
		return cuponService.insertCupon(dto);
	}

	@GetMapping(value = "/list")
	private List<CuponDTO> listAll() {
		return cuponService.listAll();
	}

	@GetMapping(value = "/listbyuser")
	private List<CuponDTO> listAllByUser(){
		return cuponService.listAlByUserl();
	}

	@GetMapping(value = "/listchange")
	private List<CuponDTO> listAllChange() {
		return cuponService.listAllChange();
	}

	@GetMapping(value = "/list/{id}")
	private CuponDTO findOne(@PathVariable("id") Integer id) {
		return cuponService.listOne(id);
	}

	@GetMapping(value = "/listname/{name}")
	private ResponseEntity<CuponDTO> findOneByName(@PathVariable("name") String name) {
		return cuponService.listOneByName(name);
	}

	@PutMapping(value = "/update/{id}")
	private ResponseEntity<?> updateCupon(@PathVariable("id") Integer id, @RequestBody @Valid CuponDTO dto)
			throws ParseException {
		return cuponService.updateCupon(id, dto);
	}

	@DeleteMapping(value = "/delete/{id}")
	private ResponseEntity<?> deleteCupon(@PathVariable("id") Integer id) {
		return cuponService.deleteCupon(id);
	}

}
