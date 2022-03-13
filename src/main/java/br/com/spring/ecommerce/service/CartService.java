package br.com.spring.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CartProductsDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
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
	
	public ResponseEntity<Products> removeFromCart(Integer id)
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		Cart cart = cartRepository.findByUserId(user.getId());
		
		Optional<Products> product = productsRepository.findById(id);

		cart.getProduct().remove(product.get());
		cartRepository.save(cart);
		
		return ResponseEntity.ok().build();
	}
	
	public List<CartProductsDTO> findAllProductsByCartId()
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Cart cart = cartRepository.findByUserId(user.getId());
		
		List<CartProductsDTO> list = new ArrayList<>();
		ProductsDTO prodDTO = new ProductsDTO();
		List<ProductsDTO> prodList = new ArrayList<>();
		
		cart.getProduct().forEach(p ->{
			BeanUtils.copyProperties(p, prodDTO);
			prodList.add(prodDTO);
		});
		
		prodList.forEach(p ->{
			List<ProductsDTO> qntd = new ArrayList<>();
			CartProductsDTO dto = new CartProductsDTO();
			qntd = prodList.stream().filter(c -> c.getId().equals(p.getId())).collect(Collectors.toList());
	
			if(!list.contains(dto))
			{
				list.add(dto);
			}
			
			dto.setQuantity(qntd.size());
			dto.setProductDTO(p);
		});	
		
		return list;
	}
	
	
}




