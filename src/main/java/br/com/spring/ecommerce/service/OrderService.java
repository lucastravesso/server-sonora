package br.com.spring.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.CartProductsDTO;
import br.com.spring.ecommerce.dto.CartTotalPriceDTO;
import br.com.spring.ecommerce.dto.OrderDTO;
import br.com.spring.ecommerce.dto.ProductsDTO;
import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.Products;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.OrderRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.FormatDate;
import br.com.spring.ecommerce.util.FormatPrice;
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
	
	public List<OrderDTO> listAll(){
		
		List<Order> order = orderRepository.findAll();
		
		return order.stream().map(o -> {
			
			OrderDTO dto = new OrderDTO();
			
			BeanUtils.copyProperties(o, dto);
			
			return dto;
		}).collect(Collectors.toList());
	}
	
	public List<OrderDTO> listAllByUserId(){
			
			User user = userRepository.findOneById(authService.getCurrent().getId());
		
			List<Order> order = orderRepository.findAllByUserId(user.getId());
			
			return order.stream().map(o -> {
				
				OrderDTO dto = new OrderDTO();
				
				BeanUtils.copyProperties(o, dto);
				
				dto.setOrderDate(FormatDate.convertDateToString(o.getOrderDate()));
				
				return dto;
			}).collect(Collectors.toList());
		}
	public ResponseEntity<OrderDTO> findOrderById(Integer id) {
		
		Order order = orderRepository.findOneById(id);

		if(Objects.nonNull(order)) {
			OrderDTO dto = new OrderDTO();
			BeanUtils.copyProperties(order, dto, "user");
			dto.setOrderDate(FormatDate.convertDateToString(order.getOrderDate()));
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	
	public CartTotalPriceDTO findAllProductsByCartId(Integer id)
	{
		Order order = orderRepository.findOneById(id);
		
		List<CartProductsDTO> cartList = new ArrayList<>();
		List<ProductsDTO> prodList = new ArrayList<>();
		
		
		order.getProducts().forEach(p ->{
			
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
