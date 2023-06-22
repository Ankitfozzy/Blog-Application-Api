package com.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BlogApplication.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
