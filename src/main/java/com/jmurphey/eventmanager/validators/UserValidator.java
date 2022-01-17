package com.jmurphey.eventmanager.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.jmurphey.eventmanager.models.User;
import com.jmurphey.eventmanager.repositories.UserRepository;

@Component
public class UserValidator implements Validator{

	@Autowired
	private UserRepository repo;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		
		if (!user.getPasswordConfirmation().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirmation", "Match");
		}
		
		if (this.repo.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Special");
		}
		
	}
	
}
