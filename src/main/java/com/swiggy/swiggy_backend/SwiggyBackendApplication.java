package com.swiggy.swiggy_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SwiggyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiggyBackendApplication.class, args);
		System.out.println("Hello");
	}

}
