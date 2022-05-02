package com.spring.food.repositories.PromotionRepository;

import com.spring.food.entities.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PromotionRepository extends MongoRepository<Promotion, String> {
}
