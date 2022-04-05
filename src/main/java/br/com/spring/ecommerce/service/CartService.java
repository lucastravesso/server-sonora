package br.com.spring.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CartProductsDTO;
import br.com.spring.ecommerce.dto.CartTotalPriceDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.model.Cart;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.CartRepository;
import br.com.spring.ecommerce.repository.ProductsRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.FormatPrice;

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

		Optional<Products> product = productsRepository.findById(id);
						
		if(product.isPresent() && product.get().getProd_quantity() > 0)
		{
			user.getCart().getProduct().add(product.get());
			cartRepository.save(user.getCart());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Products> addManyProductsToCart(Integer prodId, Integer qntd)
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());

		Optional<Products> product = productsRepository.findById(prodId);
		
		if(product.isPresent() && product.get().getProd_quantity() > 0 && qntd <= product.get().getProd_quantity())
		{
			for(int i =0; i< qntd; i++)
			{
				user.getCart().getProduct().add(product.get());
				cartRepository.save(user.getCart());
			}
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Products> removeAllFromCart()
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		user.getCart().getProduct().clear();
		cartRepository.save(user.getCart());
		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<Products> removeManyFromCart(Integer prodId)
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		List<Products> prodsToRemove = new ArrayList<>();
		
		prodsToRemove = user.getCart().getProduct().stream().filter(p -> p.getId().equals(prodId)).collect(Collectors.toList());
		
		user.getCart().getProduct().removeAll(prodsToRemove);
		cartRepository.save(user.getCart());

		return ResponseEntity.ok().build();
	}
	
	public ResponseEntity<Products> removeFromCart(Integer id)
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		Optional<Products> product = productsRepository.findById(id);

		user.getCart().getProduct().remove(product.get());
		cartRepository.save(user.getCart());
		
		return ResponseEntity.ok().build();
	}
	
	public CartTotalPriceDTO findAllProductsByCartId()
	{
		User user = userRepository.findOneById(authService.getCurrent().getId());
		Cart cart = user.getCart();
		
		List<CartProductsDTO> cartList = new ArrayList<>();
		List<ProductsDTO> prodList = new ArrayList<>();
		
		cart.getProduct().forEach(p ->{
			
			ProductsDTO prodDTO = new ProductsDTO();
			BeanUtils.copyProperties(p, prodDTO);
			prodList.add(prodDTO);
		});
		
		prodList.forEach(p ->{
			
			List<ProductsDTO> qntd = new ArrayList<>();
			CartProductsDTO dto = new CartProductsDTO();
			qntd = prodList.stream().filter(c -> c.getId().equals(p.getId())).collect(Collectors.toList());
			dto.setQuantity(qntd.size());
			dto.setProductDTO(p);
			
			if(!cartList.contains(dto))
			{
				cartList.add(dto);
			}
		});	
		
		cartList.forEach(l -> {
			
			l.setPrice(FormatPrice.getPrice(l.getProductDTO().getProd_price() * l.getQuantity()));
		});
		
		CartTotalPriceDTO ctPrice = new CartTotalPriceDTO();
		
		ctPrice.setCartProducts(cartList);
		
		ctPrice.setTotalPrice(FormatPrice.getTotalPrice(cartList)); 
		
		Integer total = cartList.stream().map(x -> x.getQuantity()).reduce((x,y) -> x+y).orElse(0);
		
		ctPrice.setTotal(total);
		
		if(CollectionUtils.isNotEmpty(cartList)) {
			
			ctPrice.setTotalPrice(FormatPrice.getTotalPrice(cartList)); 
		
		} else {
			
			ctPrice.setTotalPrice(0.00);
		}
		
		return ctPrice;
	}
	
	
}




