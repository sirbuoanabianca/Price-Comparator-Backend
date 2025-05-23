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

import com.example.spring_boot.DTO.AddShoppingListItemRequest;
import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.DTO.ShoppingListDTO;
import com.example.spring_boot.DTO.ShoppingListItemDTO;
import com.example.spring_boot.Mapper.ShoppingListItemMapper;
import com.example.spring_boot.Mapper.ShoppingListMapper;
import com.example.spring_boot.Model.ShoppingList;
import com.example.spring_boot.Model.ShoppingListItem;
import com.example.spring_boot.Services.ShoppingListService;

@RestController
@RequestMapping("/shopping-list")
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    @Autowired
    private ShoppingListMapper shoppingListMapper;

    @Autowired
    private ShoppingListItemMapper shoppingListItemMapper;

    @PostMapping("/create")
    public ResponseEntity<ShoppingListDTO> createShoppingList(@RequestBody ShoppingListDTO shoppingList) {
        ShoppingList createdShoppingListEntity = shoppingListService.createShoppingList(shoppingList);

        return ResponseEntity.ok(shoppingListMapper.toDTO(createdShoppingListEntity));
    }

    @PostMapping("/add-item")
    public ResponseEntity<ShoppingListItemDTO> addShoppingListItem(@RequestBody AddShoppingListItemRequest request) {
        ShoppingListItem createdShoppingListItemEntity = shoppingListService.addShoppingListItem(request.getProductId(), request.getShoppingListId(), request.getQuantityDesired(), request.getIgnoreBrand());

        return ResponseEntity.ok(shoppingListItemMapper.toDTO(createdShoppingListItemEntity));
    }

    @GetMapping("/split-shopping-list")
    public ResponseEntity<?> splitShoppingList(@RequestParam Integer shoppingListId) {
        List<ProductValuePerUnitDTO> products = shoppingListService.getBestDeals(shoppingListId);
        return ResponseEntity.ok(products);
    }
}
