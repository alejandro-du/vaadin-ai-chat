package com.example.chat.views.chat;

import com.example.chat.views.main.MainView;
import com.example.chat.views.main.MessageList;
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
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.vaadin.artur.Avataaar;

import java.util.Random;

@Route(value = "chat", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Chat")
@CssImport("styles/views/chat/chat-view.css")
public class ChatView extends VerticalLayout {

    private final UI ui;
    private final MessageList messageList = new MessageList();
    private final TextField message = new TextField();

    public ChatView(Bot alice2) {
        Chat chatSession = new Chat(alice2);
        ui = UI.getCurrent();

        message.setPlaceholder("Enter a message...");
        message.setWidth("100%");

        Button send = new Button(VaadinIcon.ENTER.create(), event -> {
            String text = message.getValue();
            messageList.addMessage("You", new Avataaar("Name"), text, true);
            message.clear();
            new Thread(() -> {
                try {
                    Thread.sleep(new Random().ints(1000, 3000).findFirst().getAsInt());
                    ui.access(() -> {
                        String answer = chatSession.multisentenceRespond(text);
                        messageList.addMessage("Alice", new Avataaar("Alice2"), answer, false);
                    });
                } catch (InterruptedException ignored) {
                }
            }).start();
        });
        send.addClickShortcut(Key.ENTER);

        HorizontalLayout inputLayout = new HorizontalLayout(message, send);
        inputLayout.setWidth("100%");

        VerticalLayout messagesContainer = new VerticalLayout(messageList);
        messagesContainer.setPadding(false);
        messagesContainer.setSizeFull();
        messagesContainer.getStyle().set("overflow", "auto");

        add(messagesContainer, inputLayout);
        expand(messageList);
        setSizeFull();
    }

}
