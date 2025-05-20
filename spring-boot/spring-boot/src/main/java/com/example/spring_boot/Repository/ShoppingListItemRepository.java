package com.example.spring_boot.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.DTO.ProductDTO;
import com.example.spring_boot.Model.ShoppingListItem;

@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Integer> {

    @Query("""
        SELECT new com.example.spring_boot.DTO.ProductDTO(
            sli.product.id, 
            sli.product.name, 
            sli.product.category, 
            sli.product.brand               
        )
        FROM ShoppingListItem sli
        WHERE sli.shoppingList.id = :shoppingListId
    """)
    
    List<ProductDTO> findProductsInShoppingList(Integer shoppingListId);

    ShoppingListItem findByShoppingListIdAndProductId(Integer shoppingListId, Integer productId);
}
