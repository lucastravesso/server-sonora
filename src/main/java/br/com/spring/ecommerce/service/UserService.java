package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Mapper mapper = new DozerBeanMapper();
	
	public ResponseEntity<User> insertUser(UserDTO dto)
	{
		User user = mapper.map(dto, User.class);
		user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
		Address address = mapper.map(dto.getAddressDto(), Address.class);
		address = addressRepository.save(address);
		user.setAddress(address);
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public List<UserDTO> listAll(){
		
		List<User> user = userRepository.findAll();
		
		return user.stream().map(u ->{
			UserDTO dto = mapper.map(u, UserDTO.class);
			AddressDTO aDto = mapper.map(u.getAddress(), AddressDTO.class);
			dto.setAddressDto(aDto);
			return dto;
		}).collect(Collectors.toList());
		
	}

	public ResponseEntity<?> delete(Integer Id) throws AccountNotFoundException
	{
		userRepository.findById(Id).orElseThrow(() -> new AccountNotFoundException());
		userRepository.deleteById(Id);
		return ResponseEntity.ok().build();

	}
	
	public ResponseEntity<?> updateUser(Integer id, UserDTO dto)
	{
		Optional<User> user = userRepository.findById(id);
		
		if(user.isPresent())
		{
			user.get().setFirstName(dto.getFirstName());
			user.get().setLastName(dto.getLastName());
			user.get().setCpf(dto.getCpf());
			user.get().setRg(dto.getRg());
			user.get().setBorn(dto.getBorn());
			user.get().setRegister(dto.getRegister());
			user.get().setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
			user.get().setEmail(dto.getMail());
			user.get().getAddress().setCountry(dto.getAddressDto().getCountry());
			user.get().getAddress().setState(dto.getAddressDto().getState());
			user.get().getAddress().setCity(dto.getAddressDto().getCity());
			user.get().getAddress().setDistrict(dto.getAddressDto().getDistrict());
			user.get().getAddress().setStreet(dto.getAddressDto().getStreet());
			user.get().getAddress().setNumber(dto.getAddressDto().getNumber());
			user.get().getAddress().setComplement(dto.getAddressDto().getComplement());

			userRepository.save(user.get());
			
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();	
	}
}
