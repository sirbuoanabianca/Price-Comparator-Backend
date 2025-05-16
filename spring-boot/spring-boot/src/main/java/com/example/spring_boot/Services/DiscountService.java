package com.example.spring_boot.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductDiscountDTO;
import com.example.spring_boot.Repository.DiscountRepository;
import com.example.spring_boot.Repository.ProductRepository;


@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDiscountDTO> listProductsHighestPercentage() {
        LocalDate currentDate = LocalDate.now();
        List<ProductDiscountDTO> products = productRepository.findProductsWithActiveDiscounts(currentDate);
        products.sort((p1, p2) -> Double.compare(p2.getDiscountPercentage().doubleValue(), p1.getDiscountPercentage().doubleValue()));
        return products;
    
       }

       public List<ProductDiscountDTO> listNewDiscounts() {
        LocalDate currentDate = LocalDate.now();
        LocalDate last24Hours = currentDate.minusDays(1);
        List<ProductDiscountDTO> products = productRepository.findProductsWithNewlyAddedDiscounts(last24Hours);
        return products;
    
       }
}