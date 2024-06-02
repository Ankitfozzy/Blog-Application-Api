package com.BlogApplication.Service;

import com.BlogApplication.Entity.User;
import com.BlogApplication.Exceptions.ResourceNotFoundException;
import com.BlogApplication.Exceptions.UserAlreadyExistException;
import com.BlogApplication.Payload.UserDto;
import com.BlogApplication.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private User userDtoToUser(UserDto userDto) {
    	return modelMapper.map(userDto, User.class);
		//        User user = new User();
		//		  user.setId(userdto.getId());
		//        user.setName(userDto.getName());
		//        user.setEmail(userDto.getEmail());
		//        user.setPassword(userDto.getPassword());
		//        user.setAbout(userDto.getAbout());
    }

    private UserDto userToUserDto(User user) {
    	return modelMapper.map(user, UserDto.class);
		//		UserDto userdto = new UserDto();
		//		userdto.setId(user.getId());
		//		userdto.setName(user.getName());
		//		userdto.setEmail(user.getEmail());
		//		userdto.setPassword(user.getPassword());
		//		userdto.setAbout(user.getAbout());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        // Check if a user with the given email already exists
        Optional<User> existingUserOptional = userRepo.findByEmail(userDto.getEmail());
        if (existingUserOptional.isPresent()) {
            throw new UserAlreadyExistException("User with email " + userDto.getEmail() + " already exists");
        }
        
        // Create the new user
        User user = this.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = this.userRepo.save(user);
        return this.userToUserDto(savedUser);
    }



    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        User updatedUser = this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(this::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }
}
