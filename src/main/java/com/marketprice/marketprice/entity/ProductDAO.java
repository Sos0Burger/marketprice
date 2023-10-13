package com.marketprice.marketprice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "url")
    private String url;

    @Column(name = "picture")
    private String picture;

    @ManyToOne
    @JoinColumn(name="store_id", nullable=false)
    private StoreDAO store;

    @OneToMany(mappedBy="product")
    private Set<PriceHistory> priceHistory;
}
