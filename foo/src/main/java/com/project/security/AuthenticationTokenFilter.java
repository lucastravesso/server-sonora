package com.project.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.entity.User;
import com.project.repository.UserRepository;
import com.project.service.TokenService;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UserRepository userRepository;

	public AuthenticationTokenFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.userRepository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = restoreToken(request);
		boolean valid = tokenService.isValidToken(token);
		if (valid) {
			authenticateUser(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) {
		
		Long idUser = tokenService.getIdUser(token);
		User user = userRepository.findOneById(idUser);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String restoreToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
