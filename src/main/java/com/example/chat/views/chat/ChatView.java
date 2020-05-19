package com.example.chat.views.chat;

import com.example.chat.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.shared.communication.PushMode;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.context.ApplicationContext;
import org.vaadin.artur.Avataaar;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Route(value = "chat", layout = MainView.class)
@PageTitle("Vaadin AI Chat")
@CssImport("styles/views/chat/chat-view.css")
public class ChatView extends VerticalLayout implements HasUrlParameter<String> {

    private final ApplicationContext applicationContext;
    private final ScheduledExecutorService executorService;
    private final UI ui;
    private final MessageList messageList = new MessageList();
    private final TextField message = new TextField();
    private Bot bot;
    private Chat chatSession;

    public ChatView(ApplicationContext applicationContext, ScheduledExecutorService executorService) {
        this.applicationContext = applicationContext;
        this.executorService = executorService;
        ui = UI.getCurrent();

        message.setPlaceholder("Enter a message...");
        message.setSizeFull();

        Button send = new Button(VaadinIcon.ENTER.create(), event -> sendMessage());
        send.addClickShortcut(Key.ENTER);

        HorizontalLayout inputLayout = new HorizontalLayout(message, send);
        inputLayout.addClassName("inputLayout");

        add(messageList, inputLayout);
        expand(messageList);
        setSizeFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        ui.getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
    }

    private void sendMessage() {
        String text = message.getValue();
        if (!text.trim().isEmpty()) {
            messageList.addMessage("You", new Avataaar(VaadinSession.getCurrent().getAttribute("nickname").toString()), text, true);
            message.clear();

            executorService.schedule(() -> {
                String answer = chatSession.multisentenceRespond(text);
                ui.access(() -> messageList.addMessage(bot.getName(), new Avataaar(bot.getName()), answer.isEmpty() ? "..." : answer, false));
            }, new Random().ints(1000, 3000).findFirst().getAsInt(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void setParameter(BeforeEvent event, String botName) {
        bot = (Bot) applicationContext.getBean(botName);
        chatSession = new Chat(bot);
        messageList.clear();
    }

    public String getBotName() {
        return bot.getName();
    }

}
