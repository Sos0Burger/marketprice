package com.marketprice.marketprice.view;

import com.marketprice.marketprice.entity.ProductDAO;
import com.marketprice.marketprice.service.IProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route()
@PageTitle("MarketPrice")
public class MainView extends VerticalLayout {
    @Autowired
    IProductService productService;
    private ComponentRenderer<Component, ProductDAO> productCardRenderer = new ComponentRenderer<>(
            product -> {
                HorizontalLayout cardLayout = new HorizontalLayout();
                cardLayout.setMargin(true);

                Image productImage = new Image(product.getPicture(), product.getPicture());
                productImage.setWidth("25%");
                productImage.setMaxWidth("180px");

                VerticalLayout productInfo = new VerticalLayout();

                H2 productName = new H2(product.getName());
                productName.setMinWidth("70px");

                H2 price = new H2(product.getPrice() + " руб.");

                productInfo.add(productName, price);

                Scroller descriptionScroller = new Scroller(new Text(product.getDescription()));
                descriptionScroller.setMaxHeight("150px");
                descriptionScroller.setMinWidth("100px");

                VerticalLayout buttonLayout = new VerticalLayout();
                buttonLayout.setWidth("25%");

                Button buy = new Button("Купить");
                buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                buy.setWidth("100%");
                buy.addClickListener(event-> UI.getCurrent().getPage().open(product.getUrl()));

                Button compare = new Button("Сравнить цены");
                compare.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
                compare.setWidth("100%");
                compare.addClickListener(event-> UI.getCurrent().getPage().open("http://localhost:8080/compare/"+product.getId()));

                buttonLayout.add(buy, compare);

                cardLayout.add(productImage, productInfo, descriptionScroller, buttonLayout);
                return cardLayout;
            });

    MainView(IProductService productService) {
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

        VirtualList<ProductDAO> list = new VirtualList<>();
        list.setRenderer(productCardRenderer);
        list.setWidthFull();
        list.setHeight("100%");
        add(searchLayout, list);

        searchButton.addClickListener(event->{
            list.setItems(productService.searchByName(searchField.getValue()));
            list.getDataProvider().refreshAll();
        });
        list.setItems(productService.searchByName(searchField.getValue()));
    }

}
