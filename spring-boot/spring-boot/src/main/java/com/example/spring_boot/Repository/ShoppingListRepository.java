package com.example.spring_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.Model.ShoppingList;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Integer> {

}
