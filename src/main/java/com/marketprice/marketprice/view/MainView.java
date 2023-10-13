package com.marketprice.marketprice.view;

import com.marketprice.marketprice.entity.ProductDAO;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;


@Route()
public class MainView extends VerticalLayout {
    private ComponentRenderer<Component, ProductDAO> productCardRenderer = new ComponentRenderer<>(
            product -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Image productImage = new Image(product.getPicture(), product.getPicture());
                productImage.setWidth("25%");
                productImage.setMaxWidth("180px");

                VerticalLayout productInfo = new VerticalLayout();

                H1 productName = new H1(product.getName());

                H1 price = new H1(product.getPrice() + " руб.");

                productInfo.add(productName, price);


                cardLayout.add(productImage, productInfo);
                return cardLayout;
            });

    MainView() {
        this.setHeight("100%");
        HorizontalLayout searchLayout = new HorizontalLayout();
        searchLayout.setWidthFull();

        TextField searchField = new TextField();
        searchField.setWidth("100%");
        searchField.setPlaceholder("Введите название товара");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        Button searchButton = new Button();
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.setText("Найти");

        searchLayout.add(searchField, searchButton);


        List<ProductDAO> productDAOList = new ArrayList<>();
        productDAOList.add(new ProductDAO(1L,
                "Apple Iphone 11",
                99000,
                "https://www.ozon.ru/category/apple-iphone15/",
                "https://kingstore.link/upload/iblock/cfa/60ue3p1db1249hhp0mu1kmcm69gmdvd5.jpeg",
                null,
                null));
        VirtualList<ProductDAO> list = new VirtualList<>();
        list.setItems(productDAOList);
        list.setRenderer(productCardRenderer);
        list.setWidthFull();
        list.setHeight("100%");
        add(searchLayout, list);
    }

}
