package com.spring.food.controllers;

import com.spring.food.commons.ResponseUtils;
import com.spring.food.dtos.FoodDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Food;
import com.spring.food.entities.response.ResponseData;
import com.spring.food.services.FoodService.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping(value = "/admin/food/add-new")
    public ResponseEntity<ResponseData> addNew(@ModelAttribute() FoodDTO food){

        ServiceResponse<Food> resultSave = foodService.addNew(food);
        return ResponseUtils.getResponse(resultSave);
    }

    @PutMapping("/admin/food/update/{id}")
    public  ResponseEntity<ResponseData> update(@PathVariable("id") String foodId, @ModelAttribute() FoodDTO food){
        ServiceResponse<Food> resultUpdate = foodService.update(foodId, food);
        return ResponseUtils.getResponse(resultUpdate);
    }

    @DeleteMapping("/admin/food/delete/{id}")
    public  ResponseEntity<ResponseData> delete(@PathVariable("id") String foodId){
        ServiceResponse<Food> resultDelete = foodService.delete(foodId);
        return ResponseUtils.getResponse(resultDelete);
    }

    @GetMapping("/food/fetch-all")
    public  ResponseEntity<ResponseData> getAll(){
        ServiceResponse<Object> resultFetch = foodService.fetchAll();
        return  ResponseUtils.getResponse(resultFetch);
    }

}
