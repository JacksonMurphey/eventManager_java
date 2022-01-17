package com.jmurphey.eventmanager.services;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmurphey.eventmanager.models.User;
import com.jmurphey.eventmanager.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	
// Find by ID and Email
	public User findById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public User getUserByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	
// Register and Authenticate User
	public User register(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return repo.save(user);
	}
	
	public boolean authenticate(String email, String password) {
		User user = repo.findByEmail(email);
		
		if(user == null) {
			return false;
		} else {
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	
}
