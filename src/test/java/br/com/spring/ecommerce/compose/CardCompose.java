package br.com.spring.ecommerce.compose;

import org.springframework.beans.BeanUtils;
import br.com.spring.ecommerce.dto.CardDTO;
import br.com.spring.ecommerce.model.Card;

public class CardCompose {
	
	public CardDTO buildCartDTO() {
		
		return CardDTO.builder()
				.card_name("Card Test")
				.card_flag("VISA")
				.card_number("4551.7123.1554.8312")
				.card_security("999")
				.build();
	}
	
	public Card buildCardEntity() {
		
		Card card = new Card();
		CardDTO dto = buildCartDTO();
		BeanUtils.copyProperties(dto, card);
		return card;
	}
	
	public CardDTO convertEntityToDTO(Card card) {
		
		CardDTO dto = new CardDTO();
		BeanUtils.copyProperties(card, dto);
		return dto;
	}

}
