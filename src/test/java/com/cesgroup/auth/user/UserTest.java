package com.cesgroup.auth.user;

import com.cesgroup.auth.resource.entity.Resource;
import com.cesgroup.auth.test.AbstractContextControllerTest;
import com.cesgroup.auth.user.entity.User;
import com.cesgroup.auth.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 用户测试
 * 
 * @author niklaus
 *
 */
public class UserTest extends AbstractContextControllerTest
{
	@Autowired
	public UserService userService;



	@Test
	public void getAllTest(){
		List<User> users = userService.getAll();
		for (User user : users) {
			System.out.println(user.getName());
		}
	}

	@Test
	public void getResourcesByUserIdTest(){
		String userId = "2";
		String unitId = "-1";
		List<Resource> resources = userService.getResourcesByUserId(userId, unitId);

		for (Resource resource : resources) {
			System.out.println(resource.getName());
		}
	}
	
	@Test
	public void deleteUserTest(){
		String userId = "8a949fd2587510ba015875120c2b0006";
		
		userService.delete(userId);
	}
}
