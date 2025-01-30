package com.imperialnet.el_ceibo;

import com.imperialnet.el_ceibo.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElCeiboApplication {

	@Autowired
	private  UserService  userService;

	public static void main(String[] args) {
		SpringApplication.run(ElCeiboApplication.class, args);
	}
	@PostConstruct
	public void init() {
		userService.insertAdminUser();
	}

}
