package com.spring.food.repositories.FoodRepository;

import com.spring.food.entities.Food;
import com.spring.food.entities.Type;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
    @Query(value = "{ 'foodName' : { $regex: ?0, $options: 'i' }, 'delFlg' : false}", sort = "{'foodName': -1}")
    List<Food> findTypeByNameNear(String typeName);
}
