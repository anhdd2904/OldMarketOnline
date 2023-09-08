package com.due.oldmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class OldmarketApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(OldmarketApplication.class, args);

	}
}
