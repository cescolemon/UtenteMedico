package com.example.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.ex") 
public class ExMedicoWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(ExMedicoWeb1Application.class, args);
	}

}
