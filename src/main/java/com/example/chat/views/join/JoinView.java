package com.example.chat.views.join;

import com.example.chat.views.chat.ChatView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;

@Route("join")
@CssImport("styles/views/join/join-view.css")
@RouteAlias(value = "")
@PageTitle("Vaadin AI Chat")
public class JoinView extends VerticalLayout {

    public JoinView() {
        addClassName(getClass().getSimpleName());

        H1 title = new H1("AI Chat");
        title.addClassName(getClass().getSimpleName() + "-title");

        TextField nickname = new TextField();
        nickname.addClassName(getClass().getSimpleName() + "-nickname");
        nickname.setPlaceholder("Enter your nickname...");

        Button enter = new Button("Enter", event -> enter(nickname.getValue()));
        enter.addClassName(getClass().getSimpleName() + "-enter");
        enter.addClickShortcut(Key.ENTER);

        VerticalLayout form = new VerticalLayout(title, nickname, enter);
        form.setSizeUndefined();
        form.addClassName(getClass().getSimpleName() + "-form");
        add(form);
    }

    private void enter(String nickname) {
        if (nickname.trim().isEmpty()) {
            Notification.show("Enter a nickname");
        } else {
            VaadinSession.getCurrent().setAttribute("nickname", nickname);
            UI.getCurrent().navigate(ChatView.class, "Alice");
        }
    }

}
