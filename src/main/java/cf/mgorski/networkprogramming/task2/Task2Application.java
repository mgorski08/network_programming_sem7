package cf.mgorski.networkprogramming.task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories({"cf.mgorski.networkprogramming.task2"})
@EnableScheduling
public class Task2Application {

	public static void main(String[] args) {
		SpringApplication.run(Task2Application.class, args);
	}

}
