package com.spring.food.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class FoodDTO {
    private String typeId;

    private String chefId;

    private String foodName;

    private MultipartFile image;

    private String description;

    private BigDecimal price;

    private boolean outOfStock;
}
