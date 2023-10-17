package com.marketprice.marketprice.view;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.grid.builder.RowBuilder;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.xaxis.TickPlacement;
import com.github.appreciated.apexcharts.helper.Series;
import com.marketprice.marketprice.entity.PriceHistory;
import com.marketprice.marketprice.entity.ProductDAO;
import com.marketprice.marketprice.service.IProductService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Route("compare")
public class CompareView extends VerticalLayout implements HasDynamicTitle, HasUrlParameter<String> {

    @Autowired
    IProductService productService;

    private String title = "MarketPrice";

    CompareView() {
        this.setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        ProductDAO pickedProduct = productService.findById(Integer.parseInt(s));
        title = pickedProduct.getName();
        List<ProductDAO> products = productService.findByName(pickedProduct.getName());

        HorizontalLayout productInfo = new HorizontalLayout();
        productInfo.setWidthFull();
        Image image = new Image(pickedProduct.getPicture(), pickedProduct.getPicture());
        image.setMaxWidth("25%");
        H2 description = new H2("Описание: " + pickedProduct.getDescription());

        productInfo.add(image, description);
        add(productInfo);


        add(new H1("Найденные предложения:"));


        add(createShopList(products));
    }

    public ApexCharts createLineChart(ProductDAO productDAO) {
        productDAO.getPriceHistory().sort(Comparator.comparing(PriceHistory::getDate));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        List<String> categories = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        for (var item : productDAO.getPriceHistory()
        ) {
            categories.add(dateFormat.format(item.getDate()));
            prices.add(item.getPrice());
        }

        categories.add(dateFormat.format(new Date()));
        prices.add(productDAO.getPrice());
        ApexChartsBuilder horizontalChartBuilder = new ApexChartsBuilder();
        ApexCharts lineChart = horizontalChartBuilder.withChart(
                        ChartBuilder.get()
                                .withType(Type.LINE)
                                .withZoom(ZoomBuilder.get()
                                        .withEnabled(true)
                                        .build())
                                .build())
                .withStroke(StrokeBuilder.get()
                        .withCurve(Curve.SMOOTH)
                        .build())
                .withGrid(GridBuilder.get()
                        .withRow(RowBuilder.get()
                                .withColors("#f3f3f3", "transparent")
                                .withOpacity(0.5).build()
                        ).build())
                .withForecastDataPoints(ForecastDataPointsBuilder.get().withCount(2).build())
                .withXaxis(XAxisBuilder.get()
                        .withCategories(categories)
                        .withTickPlacement(TickPlacement.BETWEEN)
                        .build())
                .withSeries(new Series<>(productDAO.getStore().getName(), prices.toArray())).build();

        lineChart.setHeight("250px");

        return lineChart;

    }

    public VerticalLayout createShopList(List<ProductDAO> products) {
        VerticalLayout cardLayout = new VerticalLayout();
        cardLayout.setMargin(true);
        cardLayout.setWidthFull();

        for (var product : products
        ) {
            VerticalLayout storeInfo = new VerticalLayout();
            storeInfo.setWidthFull();
            storeInfo.setAlignItems(Alignment.CENTER);

            Image storeImage = new Image(product.getStore().getImage(), null);
            storeImage.setHeight("200px");
            storeInfo.add(storeImage);


            Button buy = new Button("Купить за " + product.getPrice().toString() + " руб.");
            buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buy.addClickListener(event -> UI.getCurrent().getPage().open(product.getUrl()));

            storeInfo.add(buy);

            cardLayout.add(storeInfo, createLineChart(product));
        }
        return cardLayout;
    }

    @Override
    public String getPageTitle() {
        return title;
    }
}
