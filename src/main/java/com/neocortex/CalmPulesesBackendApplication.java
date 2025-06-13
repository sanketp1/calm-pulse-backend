package com.neocortex;

import com.neocortex.models.AchievementDefinition;
import com.neocortex.models.User;
import com.neocortex.models.enums.Role;
import com.neocortex.repositories.AchievementDefinitionRepository;
import com.neocortex.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CalmPulesesBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalmPulesesBackendApplication.class, args);
    }

    //TODO: temporarily setting achievements definitions here
    //TODO: temporarily creating admin user
    @Bean
    public CommandLineRunner commandLineRunner(AchievementDefinitionRepository repository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

//            User user = User.builder()
//                    .name("admin")
//                    .email("admin@admin.com")
//                    .password(passwordEncoder.encode("admin"))
//                    .phoneNumber("1234567890")
//                    .role(Role.ADMIN)
//                    .avatarURL("test.jpg")
//                    .address("test")
//                    .build();
//
//            userRepository.save(user);

            if (repository.count() == 0) {
                repository.save(AchievementDefinition.builder()
                        .title("Journal Enthusiast")
                        .description("Create 10 journal entries.")
                        .criteriaExpression("journalEntries >= 10")
                        .tag("journal_10")
                        .build());

                repository.save(AchievementDefinition.builder()
                        .title("Mood Master")
                        .description("Complete 20 mood check-ins.")
                        .criteriaExpression("moodCheckIns >= 20")
                        .tag("mood_20")
                        .build());

                repository.save(AchievementDefinition.builder()
                        .title("Resourceful")
                        .description("Use 5 resources.")
                        .criteriaExpression("resourcesUsed >= 5")
                        .tag("resource_5")
                        .build());

                repository.save(AchievementDefinition.builder()
                        .title("Consistent Streaker")
                        .description("Maintain a current streak of 7 days.")
                        .criteriaExpression("currentStreak >= 7")
                        .tag("streak_7")
                        .build());

                repository.save(AchievementDefinition.builder()
                        .title("Mood Guru")
                        .description("Maintain an average mood of at least 4.5.")
                        .criteriaExpression("averageMood >= 4.5")
                        .tag("mood_guru")
                        .build());
            }
        };
    }

}
