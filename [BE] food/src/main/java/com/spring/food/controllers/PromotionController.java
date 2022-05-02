package com.spring.food.controllers;

import com.spring.food.commons.ResponseUtils;
import com.spring.food.dtos.PromotionDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Promotion;
import com.spring.food.entities.response.ResponseData;
import com.spring.food.services.PromotionService.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @PostMapping("/admin/promotion/add-new")
    public ResponseEntity<ResponseData> addNew(@RequestBody PromotionDTO promotion){
        ServiceResponse<Promotion> resultAdd = promotionService.addNew(promotion);
        return ResponseUtils.getResponse(resultAdd);
    }

    @PutMapping("/admin/promotion/update/{id}")
    public ResponseEntity<ResponseData> update(@PathVariable("id") String promotionId, @RequestBody PromotionDTO promotion){
        ServiceResponse<Promotion> resultUpdate = promotionService.update(promotionId, promotion);
        return ResponseUtils.getResponse(resultUpdate);
    }
}
