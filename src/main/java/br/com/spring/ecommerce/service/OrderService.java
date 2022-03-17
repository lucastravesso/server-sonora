package br.com.spring.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.OrderRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.PurchaseStatus;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	@Transactional
	public ResponseEntity<?> createOrder()
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Order order = new Order();		
		
		List<Products> prods = new ArrayList<>();
		
		prods.addAll(user.getCart().getProduct());
		
		if(CollectionUtils.isNotEmpty(prods)) {
			order.setStatus(PurchaseStatus.PEDIDO_EFETUADO);
			order.setProducts(prods);
			order.setUser(user);
			
			order.setOrderDate(new Date());
			
			user.getCart().getProduct().clear();
			
			userRepository.save(user);
			orderRepository.save(order);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		
	}
	
	
}
