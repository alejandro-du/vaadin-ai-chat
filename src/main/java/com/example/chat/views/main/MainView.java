package com.example.chat.views.main;

import com.example.chat.views.chat.ChatView;
import com.example.chat.views.join.JoinView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
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

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Vaadin chat", shortName = "Vaadin chat")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push
public class MainView extends AppLayout {

    private Tabs menu;
    private Span botNameContainer;
    private Map<Tab, String> tabToBotNameMap = new HashMap<>();
    private List<Bot> bots;

    public MainView(List<Bot> bots) {
        if (VaadinSession.getCurrent().getAttribute("nickname") == null) {
            UI.getCurrent().navigate(JoinView.class);
            UI.getCurrent().getPage().reload();
            return;
        }

        this.bots = bots;
        setPrimarySection(Section.DRAWER);
        botNameContainer = new Span(new Text(""));
        addToNavbar(true, new DrawerToggle(), botNameContainer);
        menu = createMenuTabs();
        VerticalLayout menuContainer = new VerticalLayout(new Text("Bots:"), menu);
        menuContainer.getStyle().set("margin-top", "2em");
        addToDrawer(menuContainer);
    }

    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(getAvailableTabs());
        tabs.addSelectedChangeListener(event -> setBotName(tabToBotNameMap.get(event.getSelectedTab())));
        return tabs;
    }

    private void setBotName(String botName) {
        botNameContainer.removeAll();
        botNameContainer.add(new Text("Chat with " + botName));
    }

    private Tab[] getAvailableTabs() {
        return bots.stream()
                .map(bot -> createTab(bot.getName()))
                .collect(Collectors.toList()).toArray(new Tab[bots.size()]);
    }

    private Tab createTab(String botName) {
        final Tab tab = new Tab();
        Avataaar avataaar = new Avataaar(botName);
        tab.add(avataaar, new RouterLink(botName, ChatView.class, botName));
        tabToBotNameMap.put(tab, botName);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        ChatView view = (ChatView) getContent();
        setBotName(view.getBotName());
    }

}
