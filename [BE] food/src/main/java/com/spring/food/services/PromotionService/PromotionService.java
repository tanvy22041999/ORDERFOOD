package com.spring.food.services.PromotionService;

import com.spring.food.dtos.PromotionDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Promotion;

public interface PromotionService {
    ServiceResponse<Promotion> addNew(PromotionDTO promotion);

    ServiceResponse<Promotion> update(String promotionId, PromotionDTO promotion);

    ServiceResponse<Promotion> delete(String promotionId);

    ServiceResponse<Object> fetchAll();

    ServiceResponse<Promotion> fetchDetail(String promotionCode);
}
