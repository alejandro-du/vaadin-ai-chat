package com.example.chat.views.chat;

import com.example.chat.views.main.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.scopes.VaadinUIScope;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.vaadin.artur.Avataaar;

import java.util.Random;

@Route(value = "chat", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Chat")
@CssImport("styles/views/chat/chat-view.css")
@Component
@Scope(VaadinUIScope.VAADIN_UI_SCOPE_NAME)
public class ChatView extends VerticalLayout {

    private final UI ui;
    private final MessageList messageList = new MessageList();
    private final TextField message = new TextField();
    private final Chat chatSession;

    public ChatView(Bot alice2) {
        ui = UI.getCurrent();
        chatSession = new Chat(alice2);

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

    private void sendMessage() {
        String text = message.getValue();
        messageList.addMessage("You", new Avataaar("Name"), text, true);
        message.clear();

        new Thread(() -> {
            try {
                Thread.sleep(new Random().ints(1000, 3000).findFirst().getAsInt());
                String answer = chatSession.multisentenceRespond(text);
                ui.access(() -> messageList.addMessage("Alice", new Avataaar("Alice2"), answer, false));
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

}
