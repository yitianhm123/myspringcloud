package com.springcloud.demo;

import com.alibaba.druid.support.json.JSONUtils;
import com.estate.model.user.TUser;
import com.estate.service.UserService;
import com.estate.util.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplicationTests.class)
public class DemoApplicationTests {


	private UserService userService;

	@Test
	public void getUsers() {
		Result<List<TUser>> result = null;
		try {
			result = userService.queryListUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(JSONUtils.toJSONString(result));
	}

}
