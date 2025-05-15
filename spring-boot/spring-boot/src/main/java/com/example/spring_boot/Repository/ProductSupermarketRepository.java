package com.example.spring_boot.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.Model.ProductSupermarket;

@Repository
public interface ProductSupermarketRepository extends JpaRepository<ProductSupermarket, Long> {
}

