package com.marketprice.marketprice.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("compare")
public class CompareView extends VerticalLayout implements HasUrlParameter<String> {


    public CompareView(){
        add("Привет");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        add(new H1("Товар "+ s));
    }
}
