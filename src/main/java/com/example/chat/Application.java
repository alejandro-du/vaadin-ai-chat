package com.example.chat;

import org.alicebot.ab.Bot;
import org.alicebot.ab.configuration.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Bot Alice(@Value("${bot.path}") String botPath) {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Alice")
                .path(botPath)
                .build()
        );
        return bot;
    }

    @Bean
    public Bot Alice2(@Value("${bot.path}") String botPath) {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Alice2")
                .path(botPath)
                .build()
        );
        return bot;
    }

    @Bean
    public Bot Pandora(@Value("${bot.path}") String botPath) {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Pandora")
                .path(botPath)
                .build()
        );
        return bot;
    }

    @Bean
    public Bot Sara(@Value("${bot.path}") String botPath) {
        Bot bot = new Bot(BotConfiguration.builder()
                .name("Sara")
                .path(botPath)
                .build()
        );
        return bot;
    }

}
