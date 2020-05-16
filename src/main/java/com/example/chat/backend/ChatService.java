package com.example.chat.backend;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.configuration.BotConfiguration;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final Chat chatSession;

    public ChatService() {
        BotConfiguration botConfiguration = BotConfiguration.builder()
                .name("alice")
                .path("src/main/resources")
                .build();
        Bot bot = new Bot(botConfiguration);
        chatSession = new Chat(bot);
    }

    public String answer(String message) {
        return chatSession.multisentenceRespond(message);
    }

}
