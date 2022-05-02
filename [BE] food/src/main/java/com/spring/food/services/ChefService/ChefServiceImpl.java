package com.spring.food.services.ChefService;

import com.spring.food.commons.CloudinaryService;
import com.spring.food.commons.MessageManager;
import com.spring.food.comstants.SystemConstants;
import com.spring.food.dtos.ChefDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Chef;
import com.spring.food.repositories.ChefRepository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChefServiceImpl implements ChefService{
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ChefRepository chefRepository;

    @Autowired
    private MessageManager messageManager;
    private String logicCheckCreate(ChefDTO chef){
        if(chef.getChefName() == null || "".equals(chef.getChefName())) {
            return messageManager.getMessage("ERR0007", null);
        }
        return "";
    }

    private String logicCheckUpload(MultipartFile image){
        if(image != null){
            for(String type : SystemConstants.IMG_PNG){
                if(type.equals(image.getContentType())){
                    return "";
                }
            }
            return messageManager.getMessage("ERR0008", null);
        }
        else {
            return messageManager.getMessage("ERR0017", null);
        }
    }

    private String logicCheckUpdate(String chefId, ChefDTO chef){
        if(chef.getChefName() == null || "".equals(chef.getChefName())){
            return messageManager.getMessage("ERR0007", null);
        }

        boolean exist = chefRepository.existsById(chefId);
        if(!exist){
            return messageManager.getMessage("ERR0006", null);
        }

        return "";
    }

    private String logicCheckdelete(String chefId){
        boolean exist = chefRepository.existsById(chefId);
        if(!exist){
            return messageManager.getMessage("ERR0006", null);
        }
        return "";
    }

    @Override
    public ServiceResponse<Chef> createChef(ChefDTO chef) {
        ServiceResponse<Chef> result = new ServiceResponse<>();

        try{
            String error = this.logicCheckCreate(chef);

            if(!"".equals(error)){
                result.setMessageError(error);
                return  result;
            }

            Chef chefSave = new Chef();
            chefSave.setChefName(chef.getChefName());
//            if(chef.getImg() != null){
//                Map uploadResult = cloudinaryService.uploadImageProduct(chef.getImg());
//
//                if(uploadResult == null){
//                    result.setMessageError(messageManager.getMessage("ERR0009", null));
//                    return result;
//                }
//
//                chefSave.setImg(uploadResult.get("url").toString());
//                chefSave.setCloud_id(uploadResult.get("public_id").toString());
//            }if(chef.getImg() != null){
//                Map uploadResult = cloudinaryService.uploadImageProduct(chef.getImg());
//
//                if(uploadResult == null){
//                    result.setMessageError(messageManager.getMessage("ERR0009", null));
//                    return result;
//                }
//
//                chefSave.setImg(uploadResult.get("url").toString());
//                chefSave.setCloud_id(uploadResult.get("public_id").toString());
//            }

            Chef chefSaved = chefRepository.save(chefSave);
            if(chefSaved != null){
                result.setData(chefSaved);
            }
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Chef> uploadImage(String chefId, MultipartFile image) {
        ServiceResponse<Chef> result = new ServiceResponse<>();

        String error = this.logicCheckUpload(image);

        if(!"".equals(error)){
            result.setMessageError(error);
            return  result;
        }

        Optional<Chef> chefOptional = chefRepository.findById(chefId);
        if(!chefOptional.isPresent()){
            result.setMessageError(messageManager.getMessage("ERR0006", null));
        }
        else {
            Chef chefFound = chefOptional.get();
            Map uploadResult = cloudinaryService.uploadImageProduct(image);

            if(uploadResult == null){
                result.setMessageError(messageManager.getMessage("ERR0009", null));
                return result;
            }

            chefFound.setImg(uploadResult.get("url").toString());
            chefFound.setCloud_id(uploadResult.get("public_id").toString());
            chefRepository.save(chefFound);
            result.setData(chefFound);
        }
        return result;
    }

    @Override
    public ServiceResponse<Chef> fetchDetail(String chefId) {
        ServiceResponse<Chef> result = new ServiceResponse<>();
        Optional<Chef> chefOptional = chefRepository.findById(chefId);
        if(chefOptional.isPresent()){
            result.setData(chefOptional.get());
        }
        return result;
    }

    @Override
    public ServiceResponse<Chef> updateChef(String chefId, ChefDTO chef) {
        ServiceResponse<Chef> result = new ServiceResponse<>();

        try {
            String error = this.logicCheckUpdate(chefId, chef);

            if (!"".equals(error)) {
                result.setMessageError(error);
                return result;
            }

            Optional<Chef> chefFound = chefRepository.findById(chefId);
            Chef chefUpdate = chefFound.get();
            chefUpdate.setChefName(chef.getChefName());
            chefUpdate = chefRepository.save(chefUpdate);
            if(chefUpdate != null){
                result.setData(chefUpdate);
            }
        }catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }

        return result;
    }

    @Override
    public ServiceResponse<Chef> deleteChef(String chefId) {
        ServiceResponse<Chef> result = new ServiceResponse<>();
        try{
            String error = this.logicCheckdelete(chefId);

            if (!"".equals(error)) {
                result.setMessageError(error);
                return result;
            }

            Optional<Chef> chefOptional = chefRepository.findById(chefId);
            Chef chefDelete = chefOptional.get();
            chefDelete.setDelFlg(SystemConstants.DEL_FLG_ON);
            chefDelete = chefRepository.save(chefDelete);
            if(chefDelete != null){
                result.setData(chefDelete);
            }
        }catch (Exception ex) {
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }

    @Override
    public ServiceResponse<Object> getAllChef() {
        ServiceResponse<Object> result = new ServiceResponse<>();
        try{
            List<Chef> chefs = chefRepository.fetchAll();
            result.setData(chefs);
        }catch (Exception ex) {
            result.setMessageError(messageManager.getMessage("ERR0000", null));
        }
        return result;
    }
}
