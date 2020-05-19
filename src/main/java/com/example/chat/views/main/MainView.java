package com.example.chat.views.main;

import com.example.chat.views.chat.ChatView;
import com.example.chat.views.join.JoinView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.alicebot.ab.Bot;
import org.vaadin.artur.Avataaar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CssImport("./styles/shared-styles.css")
@PWA(name = "Vaadin AI chat", shortName = "Vaadin AI chat")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push
public class MainView extends AppLayout implements AfterNavigationObserver {

    private Span botNameContainer = new Span(new Text(""));
    private Tabs tabs = new Tabs();
    private Map<Tab, String> tabToBotNameMap = new HashMap<>();
    private Map<String, Tab> botNameToTabMap = new HashMap<>();
    private List<Bot> bots;

    public MainView(List<Bot> bots) {
        if (VaadinSession.getCurrent().getAttribute("nickname") == null) {
            UI.getCurrent().navigate(JoinView.class);
            UI.getCurrent().getPage().reload();
            return;
        }

        this.bots = bots;
        setPrimarySection(Section.DRAWER);
        addToNavbar(new DrawerToggle(), botNameContainer);
        createMenuTabs();
        Image vaadinImage = new Image("images/vaadin.png", "Vaadin logo");
        vaadinImage.addClassName("vaadin");
        Anchor vaadin = new Anchor("https://vaadin.com", vaadinImage);
        Div poweredBy = new Div(new Span(new Text("Powered by")), vaadin);
        Div sourceCode = new Div(
                new Anchor("https://github.com/alejandro-du/vaadin-ai-chat/tree/advanced", "Browse the source code")
        );
        Div footer = new Div(poweredBy, sourceCode);
        footer.addClassName("footer");
        VerticalLayout menu = new VerticalLayout(new H3("Bots:"), tabs, footer);
        menu.addClassName("menu");
        addToDrawer(menu);
    }

    private void createMenuTabs() {
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        tabs.addSelectedChangeListener(event -> setBotName(tabToBotNameMap.get(event.getSelectedTab())));
    }

    private void setBotName(String botName) {
        botNameContainer.removeAll();
        botNameContainer.add(new Text("Chat with " + botName));
    }

    private Tab[] getAvailableTabs() {
        return bots.stream()
                .map(Bot::getName)
                .sorted()
                .map(name -> createTab(name))
                .collect(Collectors.toList()).toArray(new Tab[bots.size()]);
    }

    private Tab createTab(String botName) {
        final Tab tab = new Tab();
        Avataaar avataaar = new Avataaar(botName);
        RouterLink link = new RouterLink(botName, ChatView.class, botName);
        link.addClassName("bot-link");
        tab.add(avataaar, link);
        tabToBotNameMap.put(tab, botName);
        botNameToTabMap.put(botName, tab);
        return tab;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        super.afterNavigation();
        ChatView view = (ChatView) getContent();
        String botName = view.getBotName();
        tabs.setSelectedTab(botNameToTabMap.get(botName));
        setBotName(botName);
    }

}
