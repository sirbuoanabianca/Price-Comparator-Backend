package com.example.spring_boot.Model;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "supermarket", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id", "name"})
}) 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supermarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 255)
    private String name; 

    @OneToMany(mappedBy = "supermarket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProductSupermarket> productSupermarketPrices = new HashSet<>();

    @OneToMany(mappedBy = "supermarket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Discount> productSupermarketDiscounts = new HashSet<>();
}

