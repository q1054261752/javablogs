package com.zkr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zkr.entity.Blog;
import com.zkr.service.UserService;
@Controller
@RequestMapping("/users")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("blog")
	public Blog constructBlog() {
		return new Blog();
	}

	@RequestMapping
	public String users(Model model){
		
		model.addAttribute("users", userService.findAll());
		return "users";
	}
	
	@RequestMapping("/{id}")
	public String users(Model model,@PathVariable int id){
		
		model.addAttribute("user", userService.findOneWithBlogs(id));
		return "user-detail";
	}
	
	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable int id) {
		
		userService.delete(id);
		return "redirect:/users.html";
	}
}
