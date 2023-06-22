package com.BlogApplication.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApplication.Payload.UserDto;
import com.BlogApplication.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST-create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @ RequestBody UserDto userdto){
		
		UserDto createUserDto = this.userService.createUser(userdto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	//PUT-update user
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE-delete user
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer uid) {
		
		this.userService.deleteUser(uid);
		return new ResponseEntity<String>("User deleted", HttpStatus.ACCEPTED);
	}
	
	
	//GET-user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
	
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//GET-user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getAllUsers(@PathVariable Integer userId){		
			return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
}
