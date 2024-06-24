package com.main.shopapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.shopapp.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer>{

	public UserDtls findByEmail(String email);

	public List<UserDtls> findAllByRole(String role);

	public UserDtls getUserByEmail(String email);
}
