package com.spring.food.controllers;

import com.spring.food.commons.ResponseUtils;
import com.spring.food.dtos.TypeDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Type;
import com.spring.food.entities.response.ResponseData;
import com.spring.food.services.TypeService.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PostMapping("/admin/type/add-new")
    public ResponseEntity<ResponseData> addNewType(@RequestBody() TypeDTO type){
        ServiceResponse<Type> resultSave = typeService.createFoodType(type);

        return ResponseUtils.getResponse(resultSave);
    }

    @PutMapping("/admin/type/update/{id}")
    public  ResponseEntity<ResponseData> updateType(@PathVariable("id") String typeId, @RequestBody() TypeDTO type){
        ServiceResponse<Type> resultUpdate = typeService.updateFoodType(typeId, type);

        return ResponseUtils.getResponse(resultUpdate);
    }

    @DeleteMapping("/admin/type/delete/{id}")
    public  ResponseEntity<ResponseData> deleteType(@PathVariable("id") String typeId){
        ServiceResponse<Type> resultDelete = typeService.deleteFoodType(typeId);
        return ResponseUtils.getResponse(resultDelete);
    }

    @GetMapping("/type/fetch-all")
    public  ResponseEntity<ResponseData> getAllType(){
        ServiceResponse<Object> resultFetch = typeService.getAllType();
        return  ResponseUtils.getResponse(resultFetch);
    }
}
