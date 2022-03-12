package br.com.spring.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.model.Cart;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.CartRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	public ResponseEntity<Products> addProductToCart(Integer id){
		
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		Cart cart = cartRepository.findByUserId(user.getId());

		Optional<Products> product = productsRepository.findById(id);
						
		if(product.isPresent() && product.get().getProd_quantity() > 0)
		{
			cart.getProduct().add(product.get());
			cartRepository.save(cart);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<?> removeFromCart(Integer id)
	{
		return ResponseEntity.ok().build();
	}
	
	
}




