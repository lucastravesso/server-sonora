package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.Order;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.OrderRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AuthenticationService authService;

	public ResponseEntity<?> insertAddress(AddressDTO dto) {
		Optional<User> user = userRepository.findById(authService.getCurrent().getId());

		Address address = new Address();
		BeanUtils.copyProperties(dto, address);

		address.setUser(user.get());

		addressRepository.save(address);

		return ResponseEntity.ok().build();

	}

	public ResponseEntity<?> deleteAddress(Integer Id) throws AccountNotFoundException {
		List<Order> order = orderRepository.findByAddressId(Id);
		if (order.isEmpty()) {
			addressRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
			addressRepository.deleteById(Id);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}

}
