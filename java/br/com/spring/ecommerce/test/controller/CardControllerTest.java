package br.com.spring.ecommerce.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.spring.ecommerce.compose.CardCompose;
import br.com.spring.ecommerce.model.Card;
import br.com.spring.ecommerce.test.application.ApplicationConfigTest;

@WithUserDetails("username")
public class CardControllerTest extends ApplicationConfigTest {

	@BeforeEach
	protected void clearRepository() {

		cardRepository.deleteAll();
	}

	@Test
	void createANewCard() throws Exception {

		String json = new ObjectMapper().writeValueAsString(new CardCompose().buildCartDTO());

		mvc.perform(post("/card/register").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		assertEquals(cardRepository.count(), 1);
	}

	@Test
	void searchForAnExistingCartById() throws Exception {

		Card card = new CardCompose().buildCardEntity();

		card = cardRepository.save(card);

		assertEquals(cardRepository.count(), 1);

		String jsonCardDTO = new ObjectMapper().writeValueAsString(new CardCompose().convertEntityToDTO(card));

		mvc.perform(get("/card/find/{id}", card.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(content().json(jsonCardDTO))
				.andExpect(status().isOk());
	}
}
