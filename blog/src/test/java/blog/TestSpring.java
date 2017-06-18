package blog;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zkr.entity.User;
import com.zkr.repository.UserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
//∏ÊÀﬂjunit spring≈‰÷√Œƒº˛    
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class TestSpring {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	
	public void sfdsdf(){
		
		User user = new User();
		user.setName("www");
		user.setPassword("24");
		
		userRepository.save(user);
		
	}
	
	@Test
	public void REW(){
		
		System.out.println(userRepository.findAll());
		
	}
	
	@Test
	public void REW1(){
		
		
		
	}
	
	
	

}
