package com.BlogApplication.Payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	@NotEmpty
	@Size(min = 2, message = "Username must be minimum of 2 characters")
	private String name;

	@Email(message = "Email Address must be valid!")
	private String email;

	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$", message = "Password must contain between 6 to 15 characters. Must be alphanumeric with both Upper and lowercase characters.")
	private String password;

	@NotEmpty
	private String about;
	
}
