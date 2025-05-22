package com.example.spring_boot.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.Model.ShoppingList;
import com.example.spring_boot.Model.ShoppingListItem;
import com.example.spring_boot.Model.User;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {
    Set<ShoppingListItem> getItemsById(Integer id);

@Query("SELECT sl.user FROM ShoppingList sl WHERE sl.id = :shoppingListId")
    public User findUserByShoppingListId(@Param("shoppingListId") Integer shoppingListId);
}
