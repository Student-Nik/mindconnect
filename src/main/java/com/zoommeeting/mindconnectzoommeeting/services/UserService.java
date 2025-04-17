package com.zoommeeting.mindconnectzoommeeting.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoommeeting.mindconnectzoommeeting.entitites.User;
import com.zoommeeting.mindconnectzoommeeting.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public void registerUser(String username, String password) {
		Optional<User> existingUser = userRepository.findByUsername(username);
		if(existingUser.isPresent()) {
			throw new RuntimeException("Username already exists!");
		}
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		userRepository.save(user);
	}
	
	public Optional<User> loginUser(String username, String password) {
	    return userRepository.findByUsername(username)
	        .filter(user -> user.getPassword().equals(password));
	}

}
