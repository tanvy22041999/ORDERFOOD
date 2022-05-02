package com.spring.food.repositories.AccountRepository;

import com.spring.food.entities.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  Account findByPhoneNumber(String phoneNumber);
}
