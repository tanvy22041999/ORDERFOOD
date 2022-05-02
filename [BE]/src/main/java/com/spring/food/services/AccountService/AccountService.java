package com.spring.food.services.AccountService;

import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Account;

public interface AccountService {

    ServiceResponse<Account> findAccountByPhoneNumber(String phongnumber);

    long count();

    ServiceResponse<Account> createNewAccount(Account account);
}
