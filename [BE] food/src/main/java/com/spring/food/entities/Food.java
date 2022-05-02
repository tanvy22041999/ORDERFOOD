package com.spring.food.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@Data
@Document(collection = "foods")
public class Food {
    @Id
    private String foodId;

    private String typeId;

    private String chefId;

    private String foodName;

    private String image;

    @JsonIgnore
    private String cloudId;

    private String description;

    private Double price;

    private String priceString;

    private boolean outOfStock;

    private boolean delFlg;

    @CreatedDate
    private DateTime createDate;

    @LastModifiedDate
    private DateTime updateDate;

    @LastModifiedBy
    private String updateUser;
}
