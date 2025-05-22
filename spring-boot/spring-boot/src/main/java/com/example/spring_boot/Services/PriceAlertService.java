package com.example.spring_boot.Services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.Model.Product;
import com.example.spring_boot.Model.ShoppingListItem;
import com.example.spring_boot.Model.User;
import com.example.spring_boot.Repository.ShoppingListItemRepository;
import com.example.spring_boot.Repository.ShoppingListRepository;

@Service
public class PriceAlertService {

    @Autowired
    private ShoppingListItemRepository shoppingListItemRepository;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private PriceService priceService;

    public ShoppingListItem setTargetPrice(Integer productId,
            Integer shoppingListId,
            BigDecimal targetPrice) {
        ShoppingListItem item = shoppingListItemRepository.findByProductIdAndShoppingListId(productId, shoppingListId);
        if (item != null) {
            item.setTargetPrice(targetPrice);
            return shoppingListItemRepository.save(item);
        }
        return null;
    }

    /**
     * Scheduled task executing at one-minute intervals This task will retrieve
     * all shopping list items for which a target price has been specified Then
     * it calculates the current price, considering any active discounts, to
     * identify items priced at or below their set target
     */
    @Scheduled(cron = "0 * * * * ?") // Runs every minute
    public void scheduledCheckTargetPrices() {

        Map<User, ProductValuePerUnitDTO> metTargetItems = getProductsAfterDiscountBelowTargetPrice();

        for (Map.Entry<User, ProductValuePerUnitDTO> entry : metTargetItems.entrySet()) {
            User user = entry.getKey();
            ProductValuePerUnitDTO productDTO = entry.getValue();
            System.out.println("User: " + user.getEmail()
                    + ", Product: " + productDTO.getName()
                    + ", Price after discount: " + productDTO.getDiscountedPrice());
        }

        if (!metTargetItems.isEmpty()) {
            // Note: the method below should be called if notification service will be implemented in the future
            // Notify users about the products that are below their target price
            // For example, it can be sent an email 
            // Also, a check must be done for prior notifications for this event to avoid over-notifying the user.

            // processNotificationsForMetTargets(metTargetItems);
        }

    }

    public Map<User, ProductValuePerUnitDTO> getProductsAfterDiscountBelowTargetPrice() {
        Map<User, ProductValuePerUnitDTO> userProductBelowTargetMap = new HashMap<>();
        List<ShoppingListItem> allItemsWithTargetPrice = shoppingListItemRepository.findByTargetPriceIsNotNull();

        for (ShoppingListItem item : allItemsWithTargetPrice) {
            Product product = item.getProduct();

            List<ProductValuePerUnitDTO> bestDealsForProduct = priceService.getBestDealByProductId(product.getId());

            if (bestDealsForProduct.isEmpty()) {
                continue;
            }

            ProductValuePerUnitDTO bestOverallDeal = bestDealsForProduct.get(0);
            if (bestOverallDeal.getDiscountedPrice().compareTo(item.getTargetPrice()) <= 0) {
                User user = shoppingListRepository.findUserByShoppingListId(item.getShoppingList().getId());
                if (user == null) {
                    continue;
                }
                userProductBelowTargetMap.put(user, bestOverallDeal);
            }
        }
        return userProductBelowTargetMap;

    }
}
