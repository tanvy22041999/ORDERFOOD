package com.spring.food.controllers;

import com.spring.food.commons.ResponseUtils;
import com.spring.food.dtos.ChefDTO;
import com.spring.food.dtos.ServiceResponse;
import com.spring.food.entities.Chef;
import com.spring.food.entities.response.ResponseData;
import com.spring.food.services.ChefService.ChefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest")
public class ChefController {
    @Autowired
    private ChefService chefService;

    @PostMapping(value = "/admin/chef/add-new")
    public ResponseEntity<ResponseData> addNew(@RequestBody() ChefDTO chef){

        ServiceResponse<Chef> resultSave = chefService.createChef(chef);
        return ResponseUtils.getResponse(resultSave);
    }

    @PostMapping(value="admin/chef/image/upload/{id}")
    public ResponseEntity<ResponseData> uploadImage(@PathVariable("id") String chefId, @RequestParam MultipartFile image){
        ServiceResponse<Chef> resultUpload = chefService.uploadImage(chefId, image);
        return ResponseUtils.getResponse(resultUpload);
    }

    @PutMapping("/admin/chef/update/{id}")
    public  ResponseEntity<ResponseData> update(@PathVariable("id") String chefId, @RequestBody() ChefDTO chef){
        ServiceResponse<Chef> resultUpdate = chefService.updateChef(chefId, chef);
        return ResponseUtils.getResponse(resultUpdate);
    }

    @DeleteMapping("/admin/chef/delete/{id}")
    public  ResponseEntity<ResponseData> delete(@PathVariable("id") String chefId){
        ServiceResponse<Chef> resultDelete = chefService.deleteChef(chefId);
        return ResponseUtils.getResponse(resultDelete);
    }

    @GetMapping("/chef/fetch-all")
    public  ResponseEntity<ResponseData> getAll(){
        ServiceResponse<Object> resultFetch = chefService.getAllChef();
        return  ResponseUtils.getResponse(resultFetch);
    }
}
