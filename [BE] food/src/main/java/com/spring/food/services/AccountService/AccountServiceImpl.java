package com.spring.food.services.AccountService;

import com.spring.food.commons.MessageManager;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Account;
import com.spring.food.repositories.AccountRepository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageManager messageManager;

    private String logicCreateCheck(Account account){
        ServiceResponse<Account> accountFounded = this.findAccountByPhoneNumber(account.getPhoneNumber());
        if(accountFounded.getData() != null){
           return messageManager.getMessage("ERR0001", null);
        }
        return "";
    }

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResponse<Account> findAccountByPhoneNumber(String phoneNumber) {
        ServiceResponse<Account> result = new ServiceResponse<Account>();
        try{
            Account account = accountRepository.findByPhoneNumber(phoneNumber);
            result.setData(account);
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0003", null));
        }
        return  result;
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public ServiceResponse<Account> createNewAccount(Account account) {
        ServiceResponse<Account> result = new ServiceResponse<Account>();
        String error = this.logicCreateCheck(account);
        if(!"".equals(error)){
            result.setMessageError(error);
            return result;
        }

        try {
            accountRepository.save(account);
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0003", null));
        }
        return result;
    }
}
