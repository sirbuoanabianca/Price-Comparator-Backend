package com.example.spring_boot.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.spring_boot.Model.Discount;
import com.example.spring_boot.Model.Product;
import com.example.spring_boot.Model.ProductSupermarket;
import com.example.spring_boot.Model.Supermarket;
import com.example.spring_boot.Repository.DiscountRepository;
import com.example.spring_boot.Repository.ProductRepository;
import com.example.spring_boot.Repository.ProductSupermarketRepository;
import com.example.spring_boot.Repository.SupermarketRepository;

@Service
public class CSVParserService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupermarketRepository supermarketRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ProductSupermarketRepository productSupermarketRepository;

    private List<Object> parseFileName(String filename) {
        String baseName = filename.replace(".csv", "").toLowerCase();
        String[] parts = baseName.split("_");
        String supermarketName;
        LocalDate date;
        boolean isDiscountFile = false;

        if (parts.length == 2) {
            supermarketName = parts[0];
            try {
                date = LocalDate.parse(parts[1], DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format in filename: " + parts[1] + " in " + filename, e);
            }
        } else if (parts.length == 3 && (parts[1].equalsIgnoreCase("discount") || parts[1].equalsIgnoreCase("discounts"))) {
            supermarketName = parts[0];
            try {
                date = LocalDate.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid date format in discount filename: " + parts[2] + " in " + filename, e);
            }
            isDiscountFile = true;
        } else {
            throw new IllegalArgumentException("Invalid filename format: " + filename
                    + ". Expected 'storename_YYYY-MM-DD.csv' or 'storename_discount_YYYY-MM-DD.csv'");
        }
        return Arrays.asList(supermarketName, date, isDiscountFile);
    }

    public void parseCSV(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("data/" + filename);
        List<Object> fileInfo = parseFileName(filename);

        String supermarketName = (String) fileInfo.get(0);
        LocalDate date = (LocalDate) fileInfo.get(1);
        boolean isDiscountFile = (boolean) fileInfo.get(2);

        try (InputStream inputStream = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)); CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                .setDelimiter(';')
                .setHeader()
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build())) {

            if (!csvParser.iterator().hasNext()) {
                throw new IOException("CSV file is empty: " + filename);
            }

            extractProductsFromCsv(csvParser, supermarketName, date, isDiscountFile);

        } catch (IOException e) {
            throw new IOException("Error reading file: " + filename, e);
        }
    }

    private void extractProductsFromCsv(CSVParser csvParser, String supermarketName, LocalDate date, boolean isDiscountFile) {

        Supermarket supermarket = supermarketRepository.findByName(supermarketName)
                .orElseGet(() -> {
                    Supermarket newSupermarket = new Supermarket();
                    newSupermarket.setName(supermarketName);
                    try {
                        return supermarketRepository.save(newSupermarket);
                    } catch (DataIntegrityViolationException e) {
                        return supermarketRepository.findByName(supermarketName).orElse(null);
                    }
                });

        for (CSVRecord record : csvParser) {
            String productId = record.get(0);
            String productName = record.get(1);
            String productCategory = record.get(2);
            String productBrand = record.get(3);

            int productIdInt = Integer.parseInt(productId.replaceAll("[^\\d]", ""));

            Product product = productRepository.findById(productIdInt)
                    .orElseGet(() -> {
                        Product newProduct = new Product();
                        newProduct.setId(productIdInt);
                        newProduct.setName(productName);
                        newProduct.setCategory(productCategory);
                        newProduct.setBrand(productBrand);
                        try {
                            return productRepository.save(newProduct);
                        } catch (DataIntegrityViolationException e) {
                            return productRepository.findById(productIdInt).orElse(null);
                        }
                    });

            if (isDiscountFile) {
                String discountStartDateStr = record.get(6);
                String discountEndDateStr = record.get(7);
                String discountPercentage = record.get(8);

                Discount discountProduct = new Discount();
                discountProduct.setProduct(product);
                discountProduct.setSupermarket(supermarket);
                discountProduct.setPercentage(new BigDecimal(discountPercentage));
                discountProduct.setFromDate(LocalDate.parse(discountStartDateStr));
                discountProduct.setToDate(LocalDate.parse(discountEndDateStr));
                try {
                    discountRepository.save(discountProduct);
                } catch (DataIntegrityViolationException e) {
                }
            } else {
                String quantity = record.get(4);
                String unit = record.get(5);
                String price = record.get(6);
                String currency = record.get(7);

                ProductSupermarket productSupermarket = new ProductSupermarket();
                productSupermarket.setProduct(product);
                productSupermarket.setSupermarket(supermarket);
                productSupermarket.setPrice(new BigDecimal(price));
                productSupermarket.setUnit(unit);
                productSupermarket.setCurrency(currency);
                productSupermarket.setQuantity(new BigDecimal(quantity));
                productSupermarket.setDate(date);
                try {
                productSupermarketRepository.save(productSupermarket);
                } catch (DataIntegrityViolationException e) {
                }
            }
        }
    }

}
