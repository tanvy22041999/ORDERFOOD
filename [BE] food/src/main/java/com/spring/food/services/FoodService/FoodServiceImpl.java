package com.spring.food.services.FoodService;

import com.spring.food.commons.CloudinaryService;
import com.spring.food.commons.MessageManager;
import com.spring.food.comstants.SystemConstants;
import com.spring.food.dtos.FoodDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Chef;
import com.spring.food.entities.Food;
import com.spring.food.entities.Type;
import com.spring.food.repositories.ChefRepository.ChefRepository;
import com.spring.food.repositories.FoodRepository.FoodRepository;
import com.spring.food.repositories.TypeRepository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FoodServiceImpl implements FoodService{
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    private String logicCheckCreate(FoodDTO food){
        if(food.getFoodName() == null || "".equals(food.getFoodName())){
            return messageManager.getMessage("ERR0010", null);
        }

        if(foodRepository.findTypeByNameNear(food.getFoodName()).size() > 0){
            return messageManager.getMessage("ERR0004", null);
        }

        if(food.getImage() != null){
            for(String type : SystemConstants.IMG_PNG){
                if(type.equals(food.getImage().getContentType())){
                    return "";
                }
            }
            return messageManager.getMessage("ERR0008", null);
        }

        if(food.getTypeId() == null || "".equals(food.getTypeId())){
            return messageManager.getMessage("ERR0011", null);
        }

        boolean typeOptional = typeRepository.existsById(food.getTypeId());
        if(!typeOptional){
            return messageManager.getMessage("ERR0013", null);
        }

        if(food.getChefId() == null || "".equals(food.getChefId())){
            return messageManager.getMessage("ERR0012", null);
        }

        boolean chefOptional = chefRepository.existsById(food.getChefId());
        if(!chefOptional){
            return  messageManager.getMessage("ERR0014", null);
        }

        return "";
    }

    @Override
    public ServiceResponse<Food> addNew(FoodDTO food) {
        ServiceResponse<Food> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckCreate(food);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Food foodSave = new Food();
            foodSave.setFoodName(food.getFoodName());
            foodSave.setTypeId(food.getTypeId());
            foodSave.setChefId(food.getChefId());
            if(food.getImage() != null){
                Map uploadResult = cloudinaryService.uploadImageProduct(food.getImage());

                if(uploadResult == null){
                    result.setMessageError(messageManager.getMessage("ERR0009", null));
                    return result;
                }

                foodSave.setImage(uploadResult.get("url").toString());
                foodSave.setCloudId(uploadResult.get("public_id").toString());
            }

            Food chefSaved = foodRepository.save(foodSave);
            if(chefSaved != null){
                result.setData(chefSaved);
            }
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Food> update(String foodId, FoodDTO food) {
        return null;
    }

    @Override
    public ServiceResponse<Food> delete(String foodId) {
        return null;
    }

    @Override
    public ServiceResponse<Object> fetchAll() {
        return null;
    }

    @Override
    public ServiceResponse<Food> turnOnOutStock(String foodId) {
        return null;
    }

    @Override
    public ServiceResponse<Food> turnOfOutStock(String foodId) {
        return null;
    }
}
