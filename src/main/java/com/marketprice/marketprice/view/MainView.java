package com.marketprice.marketprice.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route()
public class MainView extends VerticalLayout {
    MainView(){
        add(new H1("Hello"));
    }
}