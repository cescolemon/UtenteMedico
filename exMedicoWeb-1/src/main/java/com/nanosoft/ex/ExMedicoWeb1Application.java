package com.nanosoft.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nanosoft.ex") 
public class ExMedicoWeb1Application {

	public static void main(String[] args) {
		SpringApplication.run(ExMedicoWeb1Application.class, args);
	}

}
