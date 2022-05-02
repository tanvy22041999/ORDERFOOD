package com.spring.food.commons;

import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.response.ResponseData;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class ResponseUtils {
    public static final ResponseEntity<ResponseData> getResponse(ServiceResponse<?> result){
        if(result.getMessageError() != null || result.getData() == null){
            return new ResponseEntity<>(ResponseData.builder()
                    .success(false)
                    .statusCode(200)
                    .message(result.getMessageError())
                    .data(null)
                    .build(), BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseData.builder()
                .success(true)
                .message(null)
                .statusCode(200)
                .data(result.getData())
                .build(), OK);
    }
}
