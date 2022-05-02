package com.spring.food.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Data
@Document(collection = "promotions")
public class Promotion {
    @Id
    private String promotionId;

    private String promotionCode;

    private Integer promotionPercent;

    private Date expriedDate;

    private boolean delFlg;

    @CreatedDate
    private DateTime createDate;

    @LastModifiedDate
    private DateTime updateDate;

    @LastModifiedBy
    private String updateUser;
}
