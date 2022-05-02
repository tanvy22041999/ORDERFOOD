package com.spring.food.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PromotionDTO {
    private Integer promotionPercent;

    private Date expriedDate;
}
