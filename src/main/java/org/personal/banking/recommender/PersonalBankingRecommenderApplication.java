package org.personal.banking.recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PersonalBankingRecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBankingRecommenderApplication.class, args);
	}

}
