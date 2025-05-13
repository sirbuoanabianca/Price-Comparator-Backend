package com.example.spring_boot.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.spring_boot.Model.Product;

public  interface ProductRepository extends CrudRepository<Product, Integer> {}
