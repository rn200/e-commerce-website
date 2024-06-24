package com.main.shopapp.service;

import java.util.List;

import org.apache.catalina.User;

import com.main.shopapp.model.UserDtls;


public interface UserService {

	//save user
	public UserDtls saveUser(UserDtls user);
	
	//find all users
	public List<UserDtls> getAllUsers(String role);
	
	//manage user or delete user from admin page
	public boolean deleteUserByAdmin(int id);
	
	public UserDtls getUserByEmail(String email);

	public Boolean updateAccountStatus(Integer id, Boolean status);
}
