package com.spring.food.controllers;

import com.spring.food.dtos.AccountDTO;
import com.spring.food.dtos.TokenDetails;
import com.spring.food.entities.response.ResponseData;
import com.spring.food.exceptions.UserNotFoundAuthenticationException;
import com.spring.food.securities.AccountDetailsService;
import com.spring.food.securities.JwtTokenUtils;
import com.spring.food.securities.JwtUserDetails;
import com.spring.food.securities.provider.AccountAuthenticationToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/rest/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @ApiOperation("User login form (phone_number, password)")
    @PostMapping()
    public ResponseEntity<ResponseData> loginUser(@Valid @RequestBody AccountDTO dto){
        AccountAuthenticationToken authenticationToken = new AccountAuthenticationToken(
                dto.getPhoneNumber(),
                dto.getPassword(),
                true
        );
        try{
            authenticationManager.authenticate(authenticationToken);
            final JwtUserDetails userDetails = accountDetailsService
                    .loadUserByUsername(dto.getPhoneNumber());

            final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);

            return new ResponseEntity<>(ResponseData.builder()
                    .success(true)
                    .data(result)
                    .build(),OK);
        }catch (UserNotFoundAuthenticationException | BadCredentialsException ex){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .message(ex.getMessage())
                    .data(dto)
                    .build(), BAD_REQUEST);
        }
    }
}
