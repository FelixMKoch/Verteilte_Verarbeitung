package de.throsenheim;

import de.throsenheim.init.Register;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AktorApplication {

    public static void main(String[] args) {
        Register.registerToSmartHomeService();

        SpringApplication.run(AktorApplication.class, args);
    }

}
