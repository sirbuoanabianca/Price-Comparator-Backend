package com.example.spring_boot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.Model.ProductSupermarket;

@Repository
public interface ProductSupermarketRepository extends JpaRepository<ProductSupermarket, Long> {

    /*
    * Join with ProductSupermarket subquery is required to be able to select the latest price for 
    * each product_id+supermarket_id pair while also selecting other field from the product_supermarket table.
    * 
    * Then also populate with other details about the products/supermarkets.
    * 
    * Active discounts will be populated if available with a left join, or the percentage field will be left NULL
    */
@Query("""
    SELECT new com.example.spring_boot.DTO.ProductValuePerUnitDTO(
        p.id, p.name, p.brand, p.category, ps.quantity, s.name, d.percentage, ps.unit, ps.price)
    FROM ProductSupermarket ps 
    JOIN ps.product p 
    JOIN ps.supermarket s
    JOIN (
        SELECT ps2.product.id as prodId, ps2.supermarket.id as superId, MAX(ps2.date) as maxDate
        FROM ProductSupermarket ps2
        GROUP BY ps2.product.id, ps2.supermarket.id
    ) latest 
        ON p.id = latest.prodId 
        AND s.id = latest.superId 
        AND ps.date = latest.maxDate
    LEFT JOIN Discount d 
        ON d.product = p 
        AND d.supermarket = s 
        AND CURRENT_DATE BETWEEN d.fromDate AND d.toDate
    """)
    List<ProductValuePerUnitDTO> findAllProductsWithLatestPrice();

}
