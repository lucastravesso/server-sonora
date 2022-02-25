package br.com.spring.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.model.User;
import br.com.spring.ecommerce.model.UserTokenDetails;
import br.com.spring.ecommerce.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Dados inválidos!"));
		return user;
	}
	
	public UserTokenDetails getCurrent() {

		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		User user = repository.findByEmail(userDetails.getUsername()).get();
		return UserTokenDetails.builder()
				.id(user.getId())
				.email(user.getEmail())
				.isEnabled(userDetails.isEnabled())
				.profiles(user.getProfiles())
				.build(); 
	}

}
