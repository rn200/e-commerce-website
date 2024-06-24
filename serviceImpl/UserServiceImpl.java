package com.main.shopapp.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.main.shopapp.Repository.UserRepository;
import com.main.shopapp.model.UserDtls;
import com.main.shopapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDtls saveUser(UserDtls user) {
		
		UserDtls saveUser = this.userRepository.save(user);
		return saveUser;
	}

	@Override
	public List<UserDtls> getAllUsers(String role) {
		
		 List<UserDtls> alluser = userRepository.findAllByRole(role);
		 return alluser;
	}

	//manage user by admin itself
	@Override
	public boolean deleteUserByAdmin(int id) {
		UserDtls userId = this.userRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(userId)) {
			this.userRepository.delete(userId);
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public UserDtls getUserByEmail(String email) {
		UserDtls userDtls = this.userRepository.getUserByEmail(email);
		return userDtls;
	}
//status update
	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		// TODO Auto-generated method stub
		Optional<UserDtls> findById = userRepository.findById(id);
		if(findById.isPresent()) {
			UserDtls userDtls = findById.get();
			userDtls.setIsEnable(status);
			userRepository.save(userDtls);
			return true;
		}
		return false;
	}

}
