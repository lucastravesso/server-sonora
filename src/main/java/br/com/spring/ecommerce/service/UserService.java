package br.com.spring.ecommerce.service;

import java.text.ParseException;
import java.util.Date;
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
import br.com.spring.ecommerce.dto.PasswordChangeDTO;
import br.com.spring.ecommerce.dto.UserDTO;
import br.com.spring.ecommerce.dto.UserWithoutAddressDTO;
import br.com.spring.ecommerce.model.Address;
import br.com.spring.ecommerce.model.Cart;
import br.com.spring.ecommerce.model.Profile;
import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.repository.AddressRepository;
import br.com.spring.ecommerce.repository.ProfileRepository;
import br.com.spring.ecommerce.repository.UserRepository;
import br.com.spring.ecommerce.security.AuthenticationService;
import br.com.spring.ecommerce.util.FormatDate;

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
	
	@Autowired
	private EncoderService eService;
		
	private Mapper mapper = new DozerBeanMapper();
	
	public ResponseEntity<User> insertUser(UserDTO dto)
	{		
		User user = new User();
		Address address = new Address();
		Cart cart = new Cart();
		
		BeanUtils.copyProperties(dto, user, "password");
		BeanUtils.copyProperties(dto.getAddressDto(), address);
		
		user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()).toString());
		user.setAddress(address);
		user.setCart(cart);		

		List<Profile> profile = profileRepository.findAll();
		Set<Profile> role = profile.stream().filter(p -> p.getName().equals("ROLE_COMPRADOR")).collect(Collectors.toSet());
		user.setProfiles(role);
		
		addressRepository.save(address);
		userRepository.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public ResponseEntity<User> insertUserWithoutAddress(UserWithoutAddressDTO dto)
	{
		User user = mapper.map(dto, User.class);
		Cart cart = new Cart();

		user.setPassword(eService.encoder(dto.getPassword()).toString());
		
		List<Profile> profile = profileRepository.findAll();
		Set<Profile> role = profile.stream().filter(p -> p.getName().equals("ROLE_COMPRADOR")).collect(Collectors.toSet());
		user.setProfiles(role);		
		
		user.setCart(cart);	
		cart.setUser(user);
		user.setRegister(new Date());
		
		userRepository.save(user);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	public ResponseEntity<UserDTO> findUserByToken(){
	
		UserDTO uDto = new UserDTO();
		User user = userRepository.findOneById(authService.getCurrent().getId());		
				
		if(Objects.nonNull(user))
		{
			
			BeanUtils.copyProperties(user, uDto);
			uDto.setBorn(FormatDate.convertDateToString(user.getBorn()));
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
				dto.setBorn(FormatDate.convertDateToString(u.getBorn()));
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
	
	public ResponseEntity<?> updatePassword(PasswordChangeDTO dto){
		Optional<User> user = userRepository.findById(authService.getCurrent().getId());
			
		if (user.isPresent()) {

			if(eService.verifyPassword(dto.getOldPass(), user.get().getPassword())) {
				
				user.get().setPassword(eService.encoder(dto.getPassword()));
				userRepository.save(user.get());
				return ResponseEntity.status(HttpStatus.OK).build();
				
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}
	
	public ResponseEntity<?> updateUser(Integer id, UserDTO dto) throws ParseException
	{
		Optional<User> user = userRepository.findById(id);
		
		if (user.isPresent()) {
			
			user.get().setBorn(FormatDate.convertStringToDate(dto.getBorn()));

			BeanUtils.copyProperties(dto, user.get(),"password", "id");

			if (Objects.isNull(user.get().getAddress()) && Objects.nonNull(dto.getAddressDto())) {

				Address address = new Address();
				BeanUtils.copyProperties(dto.getAddressDto(), address, "id");
				addressRepository.save(address);
				user.get().setAddress(address);
				userRepository.save(user.get());

			} else if (Objects.nonNull(dto.getAddressDto())) {
				
				if (dto.getAddressDto().getCity().equals(user.get().getAddress().getCity())) {

					BeanUtils.copyProperties(dto.getAddressDto(), user.get().getAddress(), "id");
					addressRepository.save(user.get().getAddress());
					userRepository.save(user.get());
				} else {
					if (Objects.nonNull(user.get().getAddress().getId())) {
						
						BeanUtils.copyProperties(dto.getAddressDto(), user.get().getAddress(), "id");
						addressRepository.save(user.get().getAddress());
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
