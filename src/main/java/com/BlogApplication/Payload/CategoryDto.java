package com.BlogApplication.Payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;

	@NotBlank
	@Size(min = 3, message = "Min size of category title is 3 Characters.")
	private String categoryTitle;

	@NotBlank
	private String categoryDescription;
}
