package com.spring.food.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse<T>{
    private String messageError;
    private T data;
}
