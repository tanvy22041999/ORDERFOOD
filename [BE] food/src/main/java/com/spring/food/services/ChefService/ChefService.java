package com.spring.food.services.ChefService;

import com.spring.food.dtos.ChefDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Chef;
import org.springframework.web.multipart.MultipartFile;

public interface ChefService {
    ServiceResponse<Chef> createChef(ChefDTO chef);

    ServiceResponse<Chef> updateChef(String chefId, ChefDTO chef);

    ServiceResponse<Chef> deleteChef(String chefId);

    ServiceResponse<Object> getAllChef();

    ServiceResponse<Chef> uploadImage(String chefId, MultipartFile image);
}
