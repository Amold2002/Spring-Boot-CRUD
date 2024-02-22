package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.repo.UserRepo;

@RestController 
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRepo.findAll();
	}
	
	//get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id")long id) {
		return this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found : "+id));
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepo.save(user);
	}
	
	//update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user,@PathVariable("id")long id) {
		User existing=  this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found : "+id));
		existing.setFirstName(user.getFirstName());
		existing.setLastName( user.getLastName());
		existing.setEmail(user.getEmail());
		return this.userRepo.save((existing));
	}
	
	//delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id")long id){
		User existing=  this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not found : "+id));
		this.userRepo.delete(existing);
		return ResponseEntity.ok().build();
	}
	
	
	
}
