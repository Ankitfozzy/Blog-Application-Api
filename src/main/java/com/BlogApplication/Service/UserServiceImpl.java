package com.BlogApplication.Service;
import com.BlogApplication.Exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BlogApplication.Entity.User;
import com.BlogApplication.Payload.UserDto;
import com.BlogApplication.Repository.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private ModelMapper modelMapper;

	public User userDtoToUser(UserDto userdto) {
		
		User user = this.modelMapper.map(userdto, User.class);
		
//		User user = new User();
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		return user;
		
	}
	
public UserDto userDtoToUser(User user) {
		
	UserDto userdto = this.modelMapper.map(user, UserDto.class);
	
//		UserDto userdto = new UserDto();
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
		return userdto;
		
	}
	
	
	@Override
	public UserDto createUser(UserDto userdto) {
		
		User user = this.userDtoToUser(userdto);
		User savedUser = this.userrepo.save(user);
		return this.userDtoToUser(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		userrepo.save(user);
		UserDto userDto1 = this.userDtoToUser(user);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		return this.userDtoToUser(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userrepo.findAll();
		List<UserDto> userDto = users.stream().map(user-> this.userDtoToUser(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public String deleteUser(Integer userId) {
		
		User user =	this.userrepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "Id", userId));
		this.userrepo.delete(user);
		
		return "User deleted!";
	}

	

}
