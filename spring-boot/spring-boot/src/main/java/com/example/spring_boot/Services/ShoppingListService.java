package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.DTO.ShoppingListDTO;
import com.example.spring_boot.Model.Product;
import com.example.spring_boot.Model.ShoppingList;
import com.example.spring_boot.Model.ShoppingListItem;
import com.example.spring_boot.Model.User;
import com.example.spring_boot.Repository.ProductRepository;
import com.example.spring_boot.Repository.ShoppingListItemRepository;
import com.example.spring_boot.Repository.ShoppingListRepository;
import com.example.spring_boot.Repository.UserRepository;

@Service
public class ShoppingListService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private ShoppingListItemRepository shoppingListItemRepository;

    @Autowired
    private PriceService priceService;

    public ShoppingList createShoppingList(ShoppingListDTO shoppingListDTO) {
        User user = userRepository.findById(shoppingListDTO.getUser_id())
                .orElseThrow(() -> new RuntimeException(
                "User not found with id: " + shoppingListDTO.getUser_id()));

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListDTO.getName());
        shoppingList.setUser(user);

        return shoppingListRepository.save(shoppingList);
    }

    public ShoppingListItem addShoppingListItem(Integer productId, Integer shoppingListId, BigDecimal quantityDesired, Boolean ignoreBrand) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new RuntimeException(
                "Shopping list not found with id: " + shoppingListId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(
                "Product not found with id: " + productId));
        ShoppingListItem existingItem = shoppingListItemRepository
                .findByShoppingListIdAndProductId(shoppingListId, productId);

        if (existingItem != null) {
            existingItem.setQuantityDesired(existingItem.getQuantityDesired().add(quantityDesired));
            return shoppingListItemRepository.save(existingItem);
        }

        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setProduct(product);
        shoppingListItem.setShoppingList(shoppingList);
        shoppingListItem.setQuantityDesired(quantityDesired);
        shoppingListItem.setIgnoreBrand(ignoreBrand != null ? ignoreBrand : false);
        return shoppingListItemRepository.save(shoppingListItem);
    }

    /**
     *
     * @param shoppingListId which shopping list to split
     * @return List<ProductValuePerUnitDTO> with the best deal for each product
     * in the shopping list in the shopping list
     */
    public List<ProductValuePerUnitDTO> getBestDeals(Integer shoppingListId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new RuntimeException(
                "Shopping list not found with id: " + shoppingListId));
        Set<ShoppingListItem> shoppingListItems = shoppingList.getItems();

        List<ProductValuePerUnitDTO> bestDeals = new ArrayList<>();
        for (ShoppingListItem item : shoppingListItems) {
            Product product = item.getProduct();
            ProductValuePerUnitDTO bestDeal = priceService.getBestDealByProductNameAndBrandIfSet(product.getName(), item.getIgnoreBrand(), product.getBrand())
                    .stream()
                    .findFirst()
                    .orElse(null);
            if (bestDeal != null) {
                bestDeals.add(bestDeal);
            }
        }
        return bestDeals;
    }

}
