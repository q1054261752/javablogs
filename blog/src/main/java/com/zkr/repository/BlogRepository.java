package com.zkr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkr.entity.Blog;
import com.zkr.entity.User;


public interface BlogRepository extends JpaRepository<Blog, Integer>{

	List<Blog> findByUser(User user);
}
