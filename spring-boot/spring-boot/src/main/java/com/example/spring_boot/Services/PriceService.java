package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductSupermarketDTO;
import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.Repository.ProductSupermarketRepository;

@Service
public class PriceService {

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

    // This method is used to get the best deals for a product by its name and brand
    // If ignoreBrand option is set to true, it will ignore the brand filter
    // If it has an active discount percentage, it will calculate the discounted price and value per unit after discount
    public List<ProductValuePerUnitDTO> getBestDealByProductNameAndBrandIfSet(String productName, Boolean ignoreBrand, String productBrand) {
        List<ProductValuePerUnitDTO> products = productSupermarketRepository.findAllProductsWithLatestPrice()
                .stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName) &&
                        (ignoreBrand || product.getBrand().equalsIgnoreCase(productBrand)))
                .collect(Collectors.toList());

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

    public List<ProductValuePerUnitDTO> getBestDealByProductId(Integer productId) {
        List<ProductValuePerUnitDTO> products = productSupermarketRepository.findAllProductsWithLatestPrice()
                .stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .collect(Collectors.toList());

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

     public List<ProductSupermarketDTO> getPriceHistory(Integer supermarketId, String brand, String category){
        List<ProductSupermarketDTO> products = productSupermarketRepository.findProductSupermarketHistoryFiltered(supermarketId, brand, category);
        return products;
     }

}
