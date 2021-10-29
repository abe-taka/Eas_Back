package com.example.demo;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.controllers.HomeWorkController;

@SpringBootApplication
public class EasBackApplication {

	public static void main(String[] args) {
		new File(HomeWorkController.uploadDirectory).mkdir();
		SpringApplication.run(EasBackApplication.class, args);
	}

}
