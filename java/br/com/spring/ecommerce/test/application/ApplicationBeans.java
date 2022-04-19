package br.com.spring.ecommerce.test.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import br.com.spring.ecommerce.repository.CardRepository;
import br.com.spring.ecommerce.repository.UserRepository;

@AutoConfigureMockMvc
public abstract class ApplicationBeans {

	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	protected CardRepository cardRepository;
}
