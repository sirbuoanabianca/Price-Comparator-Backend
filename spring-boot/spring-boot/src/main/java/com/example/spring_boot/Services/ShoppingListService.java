package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductDTO;
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

    public ShoppingListItem addShoppingListItem(Integer productId, Integer shoppingListId, BigDecimal quantityDesired) {
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

        return shoppingListItemRepository.save(shoppingListItem);
    }

    /**
     *
     * @param shoppingListId which shopping list to split
     * @return List<ProductValuePerUnitDTO> with the best deals for each product
     * in the shopping list
     */
    public List<ProductValuePerUnitDTO> getBestDeals(Integer shoppingListId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new RuntimeException(
                "Shopping list not found with id: " + shoppingListId));
        List<ProductDTO> products = shoppingListItemRepository.findProductsInShoppingList(shoppingListId);
        return products.stream()
                .map((ProductDTO product) -> priceService.getBestDealByProductName(product.getName()).stream().findFirst().orElse(null))
                .filter(dto -> dto != null)
                .toList();
    }

}
