package br.com.spring.ecommerce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.jupiter.api.Test;

import br.com.spring.ecommerce.compose.CardCompose;
import br.com.spring.ecommerce.model.Card;
import br.com.spring.ecommerce.test.application.ApplicationConfigTest;

class CardServiceTest extends ApplicationConfigTest {

	@Test
	void testDeleteCardById() throws AccountNotFoundException {
		
		Card card = new CardCompose().buildCardEntity();
		
		card.setUser(userRepository.findAll().iterator().next());
		
		card = cardRepository.save(card);
		
		assertEquals(1, cardRepository.count());
		
		Integer id = cardRepository.findById(card.getId()).orElseThrow(() -> new AccountNotFoundException()).getId();
		
		cardRepository.deleteById(id);
		
		assertEquals(0, cardRepository.count());
	}

}
