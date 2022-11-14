package drownedtears.portfolio.rainybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class VkTelegramBotApplication {

	public static void main(String[] args) {

		SpringApplication.run(VkTelegramBotApplication.class, args);
	}

}
