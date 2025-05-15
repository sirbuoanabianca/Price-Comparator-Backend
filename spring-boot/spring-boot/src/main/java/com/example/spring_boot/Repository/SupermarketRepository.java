package com.example.spring_boot.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.Model.Supermarket;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Integer> {
       Optional<Supermarket> findByName(String name);

}
