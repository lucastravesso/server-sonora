package br.com.spring.ecommerce.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CardDTO;
import br.com.spring.ecommerce.model.Card;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.CardRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;

@Service
public class CardService {

	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
		
	public ResponseEntity<Card> insertCard(CardDTO dto){
		
		Card card = new Card();
		User user = new User();
		
		user = userRepository.findOneById(authService.getCurrent().getId());
		
		BeanUtils.copyProperties(dto, card);
		card.setUser(user);

		cardRepository.save(card);
		
		return ResponseEntity.ok().build();		
		
	}
	
	public List<CardDTO> findAll()
	{
		List<Card> cards = cardRepository.findAll();
		
		return cards.stream().map(c -> {
			CardDTO dto = new CardDTO();
			
			BeanUtils.copyProperties(c, dto, "user");
			
			return dto;
		}).collect(Collectors.toList());
	}
	
	public List<CardDTO> findAllById()
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		List<Card> cards = cardRepository.findByUserId(user);
		
		return cards.stream().map(c -> {
			CardDTO dto = new CardDTO();
			
			BeanUtils.copyProperties(c, dto, "user");
			
			return dto;
		}).collect(Collectors.toList());
	}
	
	public ResponseEntity<?> delete(Integer Id) throws AccountNotFoundException
	{
		cardRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
		cardRepository.deleteById(Id);
		return ResponseEntity.ok().build();

	}
	
	public ResponseEntity<?> updateCard(Integer id, CardDTO dto)
	{
		Optional<Card> card = cardRepository.findById(id);
		
		if(card.isPresent())
		{
			BeanUtils.copyProperties(dto, card.get(), "id");
			cardRepository.save(card.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();		
	}
	
}
