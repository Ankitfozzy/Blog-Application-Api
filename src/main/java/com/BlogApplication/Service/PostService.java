package com.BlogApplication.Service;

import java.util.List;

import com.BlogApplication.Entity.Post;
import com.BlogApplication.Payload.PostDto;
import com.BlogApplication.Payload.PostResponse;

public interface PostService {

	
	//create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete post
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
	
	//get post by id
	PostDto getPostById(Integer postId);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
}
