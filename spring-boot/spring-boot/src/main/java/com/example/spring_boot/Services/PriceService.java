package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.Repository.DiscountRepository;
import com.example.spring_boot.Repository.ProductSupermarketRepository;

@Service
public class PriceService {

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductSupermarketRepository productSupermarketRepository;

    @Autowired
    private UnitConversionService unitConversionService;

    public List<ProductValuePerUnitDTO> listProductsValuePerUnit() {
        List<ProductValuePerUnitDTO> products = productSupermarketRepository.findAllProductsWithLatestPrice();
        products.forEach(product -> {
            BigDecimal conversionRate = unitConversionService.getConversionRate(product.getUnit());
            product.setUnit(unitConversionService.getStandardUnit(product.getUnit()));
            product.setQuantity(conversionRate.multiply(product.getQuantity()));
            product.setValuePerUnit(
                    product.getPrice()
                            .divide(product.getQuantity(), 2, java.math.RoundingMode.HALF_UP)
            );

            if (product.getDiscountPercentage() == null) 
                product.setDiscountPercentage(BigDecimal.ZERO);

            product.setDiscountedPrice(
                    product.getPrice()
                            .multiply(BigDecimal.ONE.subtract(product.getDiscountPercentage().divide(new BigDecimal(100))))
            );
            product.setDiscountedValuePerUnit(
                    product.getDiscountedPrice()
                            .divide(product.getQuantity(), 2, java.math.RoundingMode.HALF_UP)
            );
        });

        products.sort((p1, p2) -> p1.getDiscountedValuePerUnit()
                .compareTo(p2.getDiscountedValuePerUnit()));
        return products;

    }

}
