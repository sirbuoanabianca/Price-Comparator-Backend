package com.example.spring_boot.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.DTO.ProductDiscountDTO;
import com.example.spring_boot.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
     @Query("SELECT new com.example.spring_boot.DTO.ProductDiscountDTO(p.id, p.name, p.brand, p.category, " +
       "s.name, d.percentage, d.fromDate, d.toDate) " +
       "FROM Product p " +
       "JOIN Discount d ON d.product = p " +
       "JOIN Supermarket s ON d.supermarket = s " +
       "WHERE d.fromDate <= :currentDate AND d.toDate >= :currentDate " +
       "ORDER BY d.percentage DESC")
    List<ProductDiscountDTO> findProductsWithActiveDiscounts(@Param("currentDate") LocalDate currentDate);


    @Query("SELECT new com.example.spring_boot.DTO.ProductDiscountDTO(p.id, p.name, p.brand, p.category, " +
       "s.name, d.percentage, d.fromDate, d.toDate) " +
       "FROM Product p " +
       "JOIN Discount d ON d.product = p " +
       "JOIN Supermarket s ON d.supermarket = s " +
       "WHERE d.fromDate >= :last24Hours " +
       "ORDER BY d.fromDate DESC")
    List<ProductDiscountDTO> findProductsWithNewlyAddedDiscounts(@Param("last24Hours") LocalDate last24Hours);

}
