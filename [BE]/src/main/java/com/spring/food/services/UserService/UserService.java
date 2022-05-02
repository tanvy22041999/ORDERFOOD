package com.spring.food.services.UserService;

import com.spring.food.dtos.PasswordDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.dtos.UserDTO;
import com.spring.food.entities.Account;
import com.spring.food.entities.User;
import com.spring.food.securities.JwtUserDetails;

public interface UserService {
    User findUserByPhoneNumber(String phoneNumber);

    ServiceResponse<UserDTO> createUser(UserDTO userDTO);

    boolean changePasswordByOTP(String userName, String newPassword);

    boolean changePasswordByLogin(JwtUserDetails userDetails, PasswordDTO passwordDTO);

    boolean updateUser(User user);
}
