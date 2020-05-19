package com.example.chat.views.chat;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import org.vaadin.artur.Avataaar;

public class MessageList extends Div {

    private final int MESSAGE_LIMIT = 50;
    private Div gap = new Div();

    public MessageList() {
        setClassName(getClass().getSimpleName());
        gap.addClassName(getClass().getSimpleName() + "-gap");
        add(gap);
    }

    public void addMessage(String from, Avataaar avatar, String text, boolean isCurrentUser) {
        if (getChildren().count() >= MESSAGE_LIMIT + 1) {
            remove(getChildren().findFirst().get());
        }

        Span fromContainer = new Span(new Text(from));
        fromContainer.addClassName(getClass().getSimpleName() + "-name");

        Div textContainer = new Div(new Html("<span>" + text + "</span>"));
        textContainer.addClassName(getClass().getSimpleName() + "-bubble");

        Div avatarContainer = new Div(avatar, fromContainer);
        avatarContainer.addClassName(getClass().getSimpleName() + "-avatar");

        Div line = new Div(avatarContainer, textContainer);
        line.addClassName(getClass().getSimpleName() + "-row");
        add(line);

        if (isCurrentUser) {
            line.addClassName(getClass().getSimpleName() + "-row-currentUser");
            textContainer.addClassName(getClass().getSimpleName() + "-bubble-currentUser");
        } else {
            line.addClassName(getClass().getSimpleName() + "-row-otherUser");
            textContainer.addClassName(getClass().getSimpleName() + "-bubble-otherUser");
        }

        remove(gap);
        add(gap);
        gap.getElement().callJsFunction("scrollIntoView");
    }

    public void clear() {
        removeAll();
    }

}
