package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.entity.User;
import com.project.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

		@Autowired
		private UserRepository repository;
	
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
			User user = repository.findByMail(username).orElseThrow(()-> new UsernameNotFoundException("Dados inválidos!"));
			return user;
		}
	}
