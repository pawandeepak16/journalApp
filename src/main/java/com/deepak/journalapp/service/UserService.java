package com.deepak.journalapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.deepak.journalapp.entity.User;
import com.deepak.journalapp.repository.UserRepository;

@Component
public class UserService {
	@Autowired
	private UserRepository userRepository;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public boolean createNewUser(User user) {
		try{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
			return true;
		}catch (Exception ex){
			return false;
		}
	}

	public void createUser(User user) {
		userRepository.save(user);
	}

	public List<User> getAll(){
		return userRepository.findAll();
	}

	public Optional<User> findById(ObjectId id){
		return userRepository.findById(id);
	}

	public void deleteById(ObjectId id) {
		userRepository.deleteById(id);
	}

	public User findByUserName(String username) {
		return userRepository.findByUserName(username);

	}

    public void createAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		userRepository.save(user);
    }
}
