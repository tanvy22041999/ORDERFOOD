package com.spring.food.controllers;

import com.spring.food.entities.response.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class PromotionController {
    @PostMapping
    public ResponseEntity<ResponseData> addNew(){
        return null;
    }
}
