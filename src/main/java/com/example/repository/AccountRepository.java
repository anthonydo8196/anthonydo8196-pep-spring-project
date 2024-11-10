package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;


// Responsible for interacting with the database
// extends JpaRepository<Account, Long> meaning we're working with account entities and primary key is Integer
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
    Account findByUsername(String username);
    Account findByUsernameAndPassword(String username, String password);
}
