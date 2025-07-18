package controlhabitos.controlhabitos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ControlHabitosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlHabitosApplication.class, args);
	}

}
