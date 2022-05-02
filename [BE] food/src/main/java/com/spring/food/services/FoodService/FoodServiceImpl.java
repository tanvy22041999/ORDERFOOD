package com.spring.food.services.FoodService;

import com.spring.food.commons.CloudinaryService;
import com.spring.food.commons.MessageManager;
import com.spring.food.comstants.SystemConstants;
import com.spring.food.dtos.FoodDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Food;
import com.spring.food.repositories.ChefRepository.ChefRepository;
import com.spring.food.repositories.FoodRepository.FoodRepository;
import com.spring.food.repositories.TypeRepository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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

    private String logicCheckCreate(FoodDTO food, boolean flgUpdate){
        if(food.getFoodName() == null || "".equals(food.getFoodName())){
            return messageManager.getMessage("ERR0010", null);
        }

        if(!flgUpdate) {
            if(foodRepository.findTypeByNameNear(food.getFoodName()).size() > 0){
                return messageManager.getMessage("ERR0004", null);
            }
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

    private String logicCheckUpdate(String foodId, FoodDTO food){
        if(!foodRepository.existsById(foodId)){
            return messageManager.getMessage("ERR0006", null);
        }

        return logicCheckCreate(food, true);
    }

    private String logicCheckDelete(String foodId){
        if(!foodRepository.existsById(foodId)){
            return  messageManager.getMessage("ERROOO6", null);
        }
        return "";
    }

    @Override
    public ServiceResponse<Food> addNew(FoodDTO food) {
        ServiceResponse<Food> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckCreate(food, false);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Food foodSave = new Food();
            foodSave.setFoodName(food.getFoodName());
            foodSave.setTypeId(food.getTypeId());
            foodSave.setChefId(food.getChefId());
            foodSave.setDescription(food.getDescription());
            if(food.getImage() != null){
                Map uploadResult = cloudinaryService.uploadImageProduct(food.getImage());

                if(uploadResult == null){
                    result.setMessageError(messageManager.getMessage("ERR0009", null));
                    return result;
                }

                foodSave.setImage(uploadResult.get("url").toString());
                foodSave.setCloudId(uploadResult.get("public_id").toString());
            }

            Food foodSaved = foodRepository.save(foodSave);
            if(foodSaved != null){
                result.setData(foodSaved);
            }
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Food> update(String foodId, FoodDTO food) {
        ServiceResponse<Food> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckUpdate(foodId, food);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Optional<Food> foodOptional = foodRepository.findById(foodId);
            Food foodUpdate = foodOptional.get();
            foodUpdate.setFoodName(food.getFoodName());
            foodUpdate.setTypeId(food.getTypeId());
            foodUpdate.setChefId(food.getChefId());
            foodUpdate.setDescription(food.getDescription());
            if(food.getImage() != null){
                Map uploadResult = cloudinaryService.uploadImageProduct(food.getImage());

                if(uploadResult == null){
                    result.setMessageError(messageManager.getMessage("ERR0009", null));
                    return result;
                }
                cloudinaryService.deleteImageProduct(foodUpdate.getCloudId());

                foodUpdate.setImage(uploadResult.get("url").toString());
                foodUpdate.setCloudId(uploadResult.get("public_id").toString());
            }

            Food foodUpdated = foodRepository.save(foodUpdate);
            if(foodUpdated != null){
                result.setData(foodUpdated);
            }
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Food> delete(String foodId) {
        ServiceResponse<Food> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckDelete(foodId);
            if(!"".equals(error)){
                result.setMessageError(error);
                return result;
            }

            Optional<Food> food = foodRepository.findById(foodId);
            Food foodDelete = food.get();
            foodDelete.setDelFlg(SystemConstants.DEL_FLG_ON);
            Food foodDeleted = foodRepository.save(foodDelete);
            if(foodDeleted != null){
                result.setData(foodDeleted);
            }
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
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
