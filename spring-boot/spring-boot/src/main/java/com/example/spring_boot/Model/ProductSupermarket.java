package com.example.spring_boot.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_supermarket", uniqueConstraints = { 
    @UniqueConstraint(columnNames = {"product_id", "supermarket_id", "date", "quantity", "unit","currency"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSupermarket { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supermarket_id", nullable = false) 
    private Supermarket supermarket;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price; 

    @Column(name = "unit", nullable = false, length = 50)
    private String unit; 

    @Column(name = "currency", length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'RON'")
    private String currency;

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;


    @Column(name = "date", nullable = false) 
    private LocalDate date; 
}

