package br.com.spring.ecommerce.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.dto.AddressDTO;
import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.dto.UserWithoutAddressDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.Profile;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.ProfileRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private AuthenticationService authService;
		
	private Mapper mapper = new DozerBeanMapper();
	
	public ResponseEntity<User> insertUser(UserDTO dto)
	{
		User user = new User();
		Address address = new Address();
		
		BeanUtils.copyProperties(dto, user, "password");
		BeanUtils.copyProperties(dto.getAddressDto(), address);
		
		user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
		user.setAddress(address);
		
		addressRepository.save(address);
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public ResponseEntity<User> insertUserWithoutAddress(UserWithoutAddressDTO dto)
	{
		User user = mapper.map(dto, User.class);
		user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
		
		List<Profile> profile = profileRepository.findAll();
		Set<Profile> role = profile.stream().filter(p -> p.getName().equals("COMPRADOR")).collect(Collectors.toSet());
		user.setProfiles(role);
		
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public ResponseEntity<UserDTO> findUserByToken(){
	
		UserDTO uDto = new UserDTO();
		User user = userRepository.findOneById(authService.getCurrent().getId());
		
		if(Objects.nonNull(user))
		{
			BeanUtils.copyProperties(user, uDto, "password");
			
			if(Objects.nonNull(user.getAddress()))
			{
				AddressDTO aDto = new AddressDTO();
				BeanUtils.copyProperties(user.getAddress(), aDto);
				uDto.setAddressDto(aDto);
				return ResponseEntity.ok(uDto);
			}
			return ResponseEntity.ok(uDto);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}	
	
	public List<UserDTO> listAll(){
		
		List<User> user = userRepository.findAll();
		
		return user.stream().map(u ->{
			
			UserDTO dto = new UserDTO();
			
			AddressDTO addressDTO = new AddressDTO();
			
			if(Objects.nonNull(u)) {
				
				BeanUtils.copyProperties(u, dto, "password");
			}
			
			if(Objects.nonNull(u.getAddress())) {
				
				BeanUtils.copyProperties(u.getAddress(), addressDTO);
				dto.setAddressDto(addressDTO);
			}

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

		if (user.isPresent()) {

			BeanUtils.copyProperties(dto, user.get(), "password", "id");

			user.get().setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());

			if (Objects.isNull(user.get().getAddress()) && Objects.nonNull(dto.getAddressDto())) {

				Address address = new Address();
				BeanUtils.copyProperties(dto.getAddressDto(), address, "id");
				addressRepository.save(address);
				user.get().setAddress(address);

			} else if (Objects.nonNull(dto.getAddressDto().getId())) {

				if (dto.getAddressDto().getId().equals(user.get().getAddress().getId())) {

					BeanUtils.copyProperties(dto.getAddressDto(), user.get().getAddress());

				} else {

					Optional<Address> address = addressRepository.findById(dto.getAddressDto().getId());
					
					if (address.isPresent()) {
						
						BeanUtils.copyProperties(dto.getAddressDto(), address.get());
						user.get().setAddress(address.get());
						
					} else {
						
						Address newAdress = new Address();
						BeanUtils.copyProperties(dto.getAddressDto(), newAdress, "id");
						addressRepository.save(newAdress);
						user.get().setAddress(newAdress);
					}
				}

			} else {

				user.get().setAddress(null);
			}
			
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	
}
