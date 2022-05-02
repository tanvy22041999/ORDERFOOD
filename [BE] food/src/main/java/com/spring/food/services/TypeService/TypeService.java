package com.spring.food.services.TypeService;

import com.spring.food.dtos.TypeDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Type;

public interface TypeService {
    ServiceResponse<Type> createFoodType(TypeDTO type);

    ServiceResponse<Type> updateFoodType(String typeId, TypeDTO type);

    ServiceResponse<Type> deleteFoodType(String typeId);

    ServiceResponse<Object> getAllType();
}
