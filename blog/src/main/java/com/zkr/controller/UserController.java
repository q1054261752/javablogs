package com.zkr.controller;


import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zkr.entity.Blog;
import com.zkr.entity.User;
import com.zkr.service.BlogService;
import com.zkr.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}
	
	@ModelAttribute("blog")
	public Blog constructBlog() {
		return new Blog();
	}
	
	
	

	
	@RequestMapping("/account")
	public String account(Model model,Principal principal){
		
		//设置  	<intercept-url pattern="/account**" access="hasRole('ROLE_USER')" />
		//访止空指针异常    访问这个url之前  spring security中必须有相应的权限在内存中
	String name = principal.getName();
		model.addAttribute("user",userService.findOneWithBlogs(name));
		
		return "account";
	}
	
	@RequestMapping(value = "/account",method = RequestMethod.POST)
	public String doAddBlog(Model model,@Valid @ModelAttribute("blog") Blog blog ,BindingResult result, Principal principal){
		if (result.hasErrors()) {
			
			//跳转到上那个controller方法上面
			return account(model, principal);
		}
		String name = principal.getName();
		
		blogService.save(blog , name);
		
		System.out.println("blog *********" + name);
		
		return "redirect:/account.html";
	}
	
	@RequestMapping("/blog/remove/{id}")
	public String removeBlog(@PathVariable int id) {
		
		Blog blog = blogService.findOne(id);
		
		blogService.delete(blog);
		return "redirect:/account.html";
	}
	
	
	
	
}
