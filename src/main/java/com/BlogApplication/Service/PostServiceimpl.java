package com.BlogApplication.Service;

import org.springframework.data.domain.Sort;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.BlogApplication.Entity.Category;
import com.BlogApplication.Entity.Post;
import com.BlogApplication.Entity.User;
import com.BlogApplication.Exceptions.ResourceNotFoundException;
import com.BlogApplication.Payload.PostDto;
import com.BlogApplication.Payload.PostResponse;
import com.BlogApplication.Repository.CategoryRepo;
import com.BlogApplication.Repository.PostRepo;
import com.BlogApplication.Repository.UserRepo;

@Service
public class PostServiceimpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Category updatedcategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(updatedcategory);

		Post savedPost = this.postRepo.save(post);

		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("postId", "Id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post updatedPost = this.postRepo.save(post);

		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("postId", "Id", postId));

		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

		Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//		Sort sort = null;
//		
//		if(sortOrder.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		}
//		else {
//			sort = Sort.by(sortBy).descending();
//		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> pagePost = this.postRepo.findAll(pageable);

		List<Post> posts = pagePost.getContent();

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("postId", "Id", postId));

		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);

		List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%" + keyword + "%");
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

}
