package br.com.spring.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.spring.ecommerce.model.User;
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
	
	public UserDetails getCurrent() {

		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof UserAuthentication) {
			UserDetails details = ((UserAuthentication) authentication).getDetails();
			return details;
		}
		return new User();
	}

}
