package com.spring.food.services.FoodService;

import com.spring.food.dtos.FoodDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Food;

public interface FoodService {
    ServiceResponse<Food> addNew(FoodDTO food);

    ServiceResponse<Food> update(String foodId, FoodDTO food);

    ServiceResponse<Food> delete(String foodId);

    ServiceResponse<Object> fetchAll();

    ServiceResponse<Food> turnOnOutStock(String foodId);

    ServiceResponse<Food> turnOfOutStock(String foodId);
}
