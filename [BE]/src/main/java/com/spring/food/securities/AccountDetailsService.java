package com.spring.food.securities;

import com.spring.food.entities.Account;
import com.spring.food.entities.User;
import com.spring.food.repositories.AccountRepository.AccountRepository;
import com.spring.food.repositories.UserRepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountDetailsService implements UserDetailsService {

    private final MessageSource messageSource;

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    public AccountDetailsService(MessageSource messageSource, AccountRepository accountRepository, UserRepository userRepository) {
        this.messageSource = messageSource;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Locale locale = LocaleContextHolder.getLocale();

        Account accountFound = accountRepository.findByPhoneNumber(phoneNumber);
        User user = userRepository.findByPhoneNumber(phoneNumber);

        if(accountFound == null || user == null){
            throw new UsernameNotFoundException(String.format(messageSource.getMessage("ERR0002", null, locale),phoneNumber));
        }

        return new JwtUserDetails(
                user.getFullName(),
                accountFound.getPhoneNumber(),
                accountFound.getPassword(),
                accountFound.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                accountFound.isActive()
        );
    }
}
