package br.com.spring.ecommerce.test.application;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import br.com.spring.ecommerce.compose.UserCompose;
import br.com.spring.ecommerce.model.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:app-test.properties")
public abstract class ApplicationConfigTest extends ApplicationBeans{
	
	@BeforeAll
	protected void init() {
       
		if(userRepository.count() == 0) {
			
			User user = new UserCompose().buildUser();
			userRepository.save(user);
		}
    }

}
