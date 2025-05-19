package com.example.spring_boot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring_boot.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
