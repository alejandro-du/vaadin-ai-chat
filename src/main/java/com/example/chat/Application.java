package com.example.chat;

import org.alicebot.ab.Bot;
import org.alicebot.ab.configuration.BotConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static final String PATH = "src/main/resources";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Bot Alice() {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Alice")
                .path(PATH)
                .build()
        );
        return bot;
    }

    @Bean
    public Bot Alice2() {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Alice2")
                .path(PATH)
                .build()
        );
        return bot;
    }

    @Bean
    public Bot Pandora() {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Pandora")
                .path(PATH)
                .build()
        );
        return bot;
    }

}
