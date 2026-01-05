package com.example.SGPT;

import org.springframework.boot.SpringApplication;

public class TestSgptApplication {

	public static void main(String[] args) {
		SpringApplication.from(SgptApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
