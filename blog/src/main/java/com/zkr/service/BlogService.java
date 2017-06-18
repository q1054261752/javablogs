package com.zkr.service;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.zkr.entity.Blog;
import com.zkr.entity.Item;
import com.zkr.entity.User;
import com.zkr.exception.RssException;
import com.zkr.repository.BlogRepository;
import com.zkr.repository.ItemRepository;
import com.zkr.repository.UserRepository;
@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RssService rssService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	
	public void saveItems(Blog blog){
		
		try {
			List<Item> items = rssService.getItems(blog.getUrl());
		    for (Item item:items) {
		    	
				Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
				if (savedItem == null) {
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
		} catch (RssException e) {
			e.printStackTrace();
		}
	}
	
	// 1 hour = 60 second * 60 minutes * 1000
	@Scheduled(fixedDelay=3600000)
	public void reloadBlogs(){
		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs) {
			saveItems(blog);
			System.out.println("******************重新获取博客*******************");
		}
	}
	
	@Transactional
	public void save(Blog blog, String name) {
		
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
		saveItems(blog);
		
	}
	
	@Transactional
	public void delete(int id) {
	
		blogRepository.delete(id);
	}
	public Blog findOne(int id) {
	
		Blog blog = blogRepository.findOne(id);
		return blog;
	}
	
	@PreAuthorize(value = "#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		
		blogRepository.delete(blog);
	}

}
