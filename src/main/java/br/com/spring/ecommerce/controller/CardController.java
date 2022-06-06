package br.com.spring.ecommerce.controller;

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

import br.com.spring.ecommerce.dto.CardDTO;
import br.com.spring.ecommerce.model.Card;
import br.com.spring.ecommerce.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {

	@Autowired
	private CardService cardService;
	
	@PostMapping("/register")
	public ResponseEntity<Card> insertCard(@RequestBody @Valid CardDTO dto) 
	{
		return cardService.insertCard(dto);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<CardDTO> findById(@PathVariable("id") Integer id)
	{
		return cardService.findById(id);
	}
	
	@GetMapping("/listall")
	public List<CardDTO> findAll(){
		return cardService.findAll();
	}
	
	@GetMapping("/listallbyid")
	public List<CardDTO> findAllById(){
		return cardService.findAllById();
	}
	
	@GetMapping("/listallbyid/{id}")
	public List<CardDTO> findAllByUserId(@PathVariable("id") Integer id){
		return cardService.findAllByUserId(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws AccountNotFoundException
	{
		return cardService.delete(id);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCard(@PathVariable("id") Integer id, @RequestBody @Valid CardDTO dto)
	{
		return cardService.updateCard(id, dto);
	}
	
}
