package me.akshaygupta.connectcard;

import me.akshaygupta.connectcard.dto.RegisterRequest;
import me.akshaygupta.connectcard.model.MongoTest;
import me.akshaygupta.connectcard.model.Profile;
import me.akshaygupta.connectcard.model.User;
import me.akshaygupta.connectcard.repository.MongoTestRepository;
import me.akshaygupta.connectcard.repository.ProfileRepository;
import me.akshaygupta.connectcard.repository.UserRepository;
import me.akshaygupta.connectcard.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConnectcardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectcardApplication.class, args);
	}
	@Bean
	CommandLineRunner testInsert(
			MongoTestRepository repo,
			UserRepository userRepo,
			ProfileRepository profileRepo,
			UserService userService
	) {
		return args -> {
			MongoTest test  = MongoTest.builder().name("Hello Test mongo").build();
			repo.save(test);
			System.out.println("Inserted ID: " + test.getId());
			System.out.println("Total records: " + repo.count());

//			// add user
//
//			userService.register(RegisterRequest.builder()
//					.email("akshay@gmail.com")
//					.username("akshay")
//					.password("hashed")
//					.build());
//
//			System.out.println(userRepo.findByUsername("akshay").isPresent());

//			// add profile
//			profileRepo.save(Profile.builder()
//					.userId("123")
//					.username("akshay")
//					.displayName("Akshay Gupta")
//					.build());
		};
	}
}
