package br.com.spring.ecommerce.controller;

import java.text.ParseException;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
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

import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.dto.UserWithoutAddressDTO;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/register")
	public ResponseEntity<User> insertUser(@RequestBody @Valid UserDTO dto)
	{
		return userService.insertUser(dto);
	}
	
	@PostMapping(value = "/registerNoAddress")
	public ResponseEntity<User> insertUser(@RequestBody @Valid UserWithoutAddressDTO dto)
	{
		return userService.insertUserWithoutAddress(dto);
	}
	
	@GetMapping(value = "/list")
	public List<UserDTO> listAll()
	{
		return userService.listAll();
	}
	
	@GetMapping(value = "/findByToken")
	public ResponseEntity<UserDTO> findOneByToken()
	{
		return userService.findUserByToken();
	}
	
	@DeleteMapping(value ="/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) throws AccountNotFoundException
	{
		return userService.delete(id);
	}
	
	@PutMapping(value ="/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Integer id,@RequestBody @Valid UserDTO dto) throws ParseException
	{
		return userService.updateUser(id, dto);
	}
	
}
