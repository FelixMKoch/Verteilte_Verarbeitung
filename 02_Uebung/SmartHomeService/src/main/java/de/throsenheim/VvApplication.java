package de.throsenheim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VvApplication {

	public static void main(String[] args) {
		SpringApplication.run(VvApplication.class, args);
	}

}
