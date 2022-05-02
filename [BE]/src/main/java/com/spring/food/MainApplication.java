package com.spring.food;

import com.spring.food.entities.Account;
import com.spring.food.entities.User;
import com.spring.food.repositories.UserRepository.UserRepository;
import com.spring.food.services.AccountService.AccountService;
import com.spring.food.services.UserService.UserService;
import com.spring.food.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling

public class MainApplication implements CommandLineRunner {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(accountService.count() <= 0){
            Account accountAdmin = new Account();
            accountAdmin.setPhoneNumber("0123456789");
            accountAdmin.setActive(true);
            accountAdmin.setPassword("123456");
            accountAdmin.setRoles(Arrays.asList(EnumRole.ROLE_ADMIN.toString()));
            accountAdmin.setUpdatedDate(LocalDateTime.now());
            accountService.createNewAccount(accountAdmin);

            User userAdmin = new User();
            userAdmin.setFullName("Admin");
            userAdmin.setPhoneNumber(accountAdmin.getPhoneNumber());
            userRepository.save(userAdmin);

        }
    }

}
