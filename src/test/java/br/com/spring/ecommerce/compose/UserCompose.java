package br.com.spring.ecommerce.compose;

import java.util.HashSet;
import java.util.Set;

import br.com.spring.ecommerce.model.Profile;
import br.com.spring.ecommerce.model.User;

public class UserCompose{

	public User buildUser(){
		
		Set<Profile> profiles = new HashSet<>();
		Profile profile = new Profile();
		profile.setIdProfile(1);
		profile.setName("COMPRADOR");
		
		User user = new User();
		user.setEmail("username");
		user.setProfiles(profiles);
		return user;
	}
}
