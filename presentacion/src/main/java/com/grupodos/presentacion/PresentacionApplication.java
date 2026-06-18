package com.grupodos.presentacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PresentacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresentacionApplication.class, args);
	}

}
