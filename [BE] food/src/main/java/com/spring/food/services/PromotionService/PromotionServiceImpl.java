package com.spring.food.services.PromotionService;

import com.spring.food.commons.MessageManager;
import com.spring.food.comstants.SystemConstants;
import com.spring.food.dtos.PromotionDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Promotion;
import com.spring.food.repositories.PromotionRepository.PromotionRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PromotionServiceImpl implements PromotionService{
    @Autowired
    private MessageManager messageManager;

    @Autowired
    private PromotionRepository promotionRepository;

    private String logicCheckCreate(PromotionDTO promotion){
        if(promotion.getExpriedDate() == null || (promotion.getExpriedDate().compareTo(LocalDateTime.now().toDate()) == -1)){
            return messageManager.getMessage("ERR0015", null);
        }

        if(promotion.getPromotionPercent() == null || promotion.getPromotionPercent() <=0 || promotion.getPromotionPercent() > 100){
            return messageManager.getMessage("ERR0016", null);
        }
        return "";
    }

    private String logicCheckUpdate(String promotionId, PromotionDTO promotion){
        if(!promotionRepository.existsById(promotionId)){
            return messageManager.getMessage("ERR0006", null);
        }

        return this.logicCheckCreate(promotion);
    }

    private String logicCheckDelete(String promotionId){
        if(!promotionRepository.existsById(promotionId)){
            return messageManager.getMessage("ERR0006", null);
        }
        return "";
    }

    @Override
    public ServiceResponse<Promotion> addNew(PromotionDTO promotion) {
        ServiceResponse<Promotion> result = new ServiceResponse<>();
        try{
            String error = this.logicCheckCreate(promotion);
            if(!"".equals(error)){
                result.setMessageError(error);
                return result;
            }

            String code = UUID.randomUUID().toString().toUpperCase().substring(0,6);
            Promotion promotionSave = new Promotion();
            promotionSave.setPromotionCode(code);
            promotionSave.setPromotionPercent(promotion.getPromotionPercent());
            promotionSave.setExpriedDate(promotion.getExpriedDate());
            promotionSave.setDelFlg(SystemConstants.DEL_FLG_OFF);

            Promotion promotionSaved = promotionRepository.save(promotionSave);
            if(promotionSaved != null){
                result.setData(promotionSaved);
            }
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Promotion> update(String promotionId, PromotionDTO promotion) {
        ServiceResponse<Promotion> result = new ServiceResponse<>();
        try{
            String error = this.logicCheckUpdate(promotionId, promotion);
            if(!"".equals(error)){
                result.setMessageError(error);
                return result;
            }

            Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
            Promotion promotionUpdate = promotionOptional.get();
            promotionUpdate.setExpriedDate(promotion.getExpriedDate());
            promotionUpdate.setPromotionPercent(promotion.getPromotionPercent());
            Promotion promotionUpdated = promotionRepository.save(promotionUpdate);
            if(promotionUpdated != null){
                result.setData(promotionUpdated);
            }
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Promotion> delete(String promotionId) {
        ServiceResponse<Promotion> result = new ServiceResponse<>();
        try {
            String error = this.logicCheckDelete(promotionId);
            if (!"".equals(error)) {
                result.setMessageError(error);
                return result;
            }

            Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
            Promotion promotionUpdate = promotionOptional.get();
            Promotion promotionUpdated = promotionRepository.save(promotionUpdate);
            if (promotionUpdated != null) {
                result.setData(promotionUpdated);
            }
        }catch (Exception ex){

        }
            return result;
    }

    @Override
    public ServiceResponse<Object> fetchAll() {
        return null;
    }

    @Override
    public ServiceResponse<Promotion> fetchDetail(String promotionCode) {
        return null;
    }
}
