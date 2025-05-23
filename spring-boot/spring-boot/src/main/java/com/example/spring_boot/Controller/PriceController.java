package com.example.spring_boot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.ProductSupermarketDTO;
import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.DTO.SetTargetPriceRequest;
import com.example.spring_boot.Mapper.ShoppingListItemMapper;
import com.example.spring_boot.Model.ShoppingListItem;
import com.example.spring_boot.Services.PriceAlertService;
import com.example.spring_boot.Services.PriceService;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceAlertService priceAlertService;

    @Autowired
    private ShoppingListItemMapper shoppingListItemMapper;

    @GetMapping("/value-per-unit/all-products")
    public ResponseEntity<?> listProductsValuePerUnit() {

        List<ProductValuePerUnitDTO> products = priceService.listProductsValuePerUnit();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/value-per-unit/by-product-name")
    public ResponseEntity<?> getValuePerUnitProductByName(@RequestParam String productName) {
        List<ProductValuePerUnitDTO> products = priceService.getBestDealByProductNameAndBrandIfSet(productName, true, null);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-trends")
    public ResponseEntity<?> getPriceHistory(@RequestParam (name = "supermarketId", required = false) Integer supermarketId,
            @RequestParam (name = "brand", required = false) String brand, @RequestParam(name = "category", required = false) String category ) {
        List<ProductSupermarketDTO> products = priceService.getPriceHistory(supermarketId, brand, category);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/set-target-price")
    public ResponseEntity<?> setTargetPrice(@RequestBody SetTargetPriceRequest request) {
        ShoppingListItem item = priceAlertService.setTargetPrice(request.getProductId(), request.getShoppingListId(), request.getTargetPrice());
        return ResponseEntity.ok(shoppingListItemMapper.toDTO(item));
    }

}
