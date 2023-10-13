package com.marketprice.marketprice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity(name = "stores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy="store")
    private Set<ProductDAO> products;
}
