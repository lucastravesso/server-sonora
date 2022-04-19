package br.com.spring.ecommerce.test.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import br.com.spring.ecommerce.compose.CardCompose;
import br.com.spring.ecommerce.dto.CardDTO;
import br.com.spring.ecommerce.model.Card;

public class CardUnit {
	
	@Test
	public void findAllCards()
	{
		List<Card> cards = new ArrayList<Card>();
		Card card = new CardCompose().buildCardEntity();
		cards.add(card);
		
		List<CardDTO> dtos = cards.stream().map(c -> {
			
			CardDTO dto = new CardDTO();
			BeanUtils.copyProperties(c, dto, "user");
			return dto;
		}).collect(Collectors.toList());
		
		assertEquals(cards.size(), dtos.size());
		assertNull(dtos.iterator().next().getUser());
		assertNotNull(dtos.iterator().next());
	}
}
