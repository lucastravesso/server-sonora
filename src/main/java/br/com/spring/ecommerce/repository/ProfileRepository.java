package br.com.spring.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.spring.ecommerce.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

}
