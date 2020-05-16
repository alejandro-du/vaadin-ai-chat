package com.example.chat.views.main;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.artur.Avataaar;

public class MessageList extends VerticalLayout {

    public MessageList() {
        getStyle().set("display", "block");
        setHeight(null);
    }

    public void addMessage(String from, Avataaar avatar, String text, boolean isCurrentUser) {
        Span fromContainer = new Span(new Text(from));
        fromContainer.getStyle().set("font-weight", "bold");

        FlexLayout textContainer = new FlexLayout(new Text(text));
        textContainer.getStyle().set("margin", ".5em");
        textContainer.getStyle().set("padding", "1em");
        textContainer.getStyle().set("border-radius", "var(--lumo-border-radius-s)");

        VerticalLayout avatarContainer = new VerticalLayout(avatar, fromContainer);
        avatarContainer.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        avatarContainer.setSizeUndefined();
        avatarContainer.setSpacing(false);
        avatarContainer.setPadding(false);

        FlexLayout line = new FlexLayout(avatarContainer, textContainer);
        line.setAlignItems(Alignment.CENTER);
        add(line);

        if (isCurrentUser) {
            line.getStyle().set("flex-direction", "row-reverse");
            textContainer.getStyle().set("background-color", "var(--lumo-primary-text-color)");
            textContainer.getStyle().set("color", "var(--lumo-base-color)");
        } else {
            textContainer.getStyle().set("background-color", "var(--lumo-shade-20pct)");
        }

        line.getElement().callJsFunction("scrollIntoView");
    }

}
