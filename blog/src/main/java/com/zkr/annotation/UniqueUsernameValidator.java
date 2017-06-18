package com.zkr.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.zkr.repository.UserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String>{  //<annotion,T>  ע���࣬У���ֶε�����

	@Autowired
	private UserRepository UserRepository;
	
	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		
		if (UserRepository == null) {
			return true;
		}
		
		return UserRepository.findByName(username) == null;
	}

}
