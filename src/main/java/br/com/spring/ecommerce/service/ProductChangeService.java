package br.com.spring.ecommerce.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.ProductChangeDTO;
import br.com.spring.ecommerce.model.ProductChange;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.ProductChangeRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.ChangeStatus;

@Service
public class ProductChangeService {

	
	@Autowired
	private ProductChangeRepository productChangeReposioty;
	
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	public ResponseEntity<?> createChange(Integer id)
	{
		ProductChange pChange = new ProductChange();
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Optional<Products> product = productsRepository.findById(id);
		
		pChange.setUser(user);
		pChange.setProduct(product.get());
		pChange.setChange_date(new Date());
		pChange.setStatus(ChangeStatus.AGUARDANDO_APROVAÇÃO);
		
		productChangeReposioty.save(pChange);
		return ResponseEntity.ok().build();
	}
	
	@Transactional
	public ResponseEntity<?> changeStatus(Integer id, ProductChangeDTO dto)
	{
		Optional<ProductChange> pChange = productChangeReposioty.findById(id);
		
		pChange.get().setStatus(dto.getStatus());
		
		productChangeReposioty.save(pChange.get());
		
		return ResponseEntity.ok().build();
	}
	
}
