package com.project.service;

import java.util.List;
import java.util.Objects;
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

import com.project.dto.AddressDTO;
import com.project.dto.UserDTO;
import com.project.dto.UserLoginDTO;
import com.project.entity.Address;
import com.project.entity.User;
import com.project.repository.AddressRepository;
import com.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private Mapper mapper = new DozerBeanMapper();
	
	
	public ResponseEntity<User> insertUser(UserDTO dto)
	{
		User user = mapper.map(dto, User.class, "password");
		System.out.println(user);
		Address address = mapper.map(dto.getAddressDto(), Address.class);
		address = addressRepository.save(address);
		user.setAddress(address);
		user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
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
			user.get().setLogin(dto.getLogin());
			user.get().setPassword(dto.getPassword());
			user.get().setMail(dto.getMail());
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
	
	public ResponseEntity<User> userLogin(UserLoginDTO dto)
	{
		User user = userRepository.findOneByEmail(dto.getMail());
		
		if(Objects.nonNull(user))
		{
			if(dto.getPassword().equals(user.getPassword()))
			{
				return ResponseEntity.ok().build();
			}else {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
}
