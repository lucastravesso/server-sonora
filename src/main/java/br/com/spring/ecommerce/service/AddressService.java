package br.com.spring.ecommerce.service;

import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;

@Service	
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationService authService;
	
	public ResponseEntity<?> insertAddress(AddressDTO dto){
		Optional<User> user = userRepository.findById(authService.getCurrent().getId());
		
		Address address = new Address();
		BeanUtils.copyProperties(dto, address);
		
		address.setUser(user.get());
		
		addressRepository.save(address);
		
		return ResponseEntity.ok().build();
		
	}
	
	public ResponseEntity<?> deleteAddress(Integer Id) throws AccountNotFoundException
	{
		addressRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
		addressRepository.deleteById(Id);
		return ResponseEntity.ok().build();

	}
	
	
}
