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

@Getter
@Setter
@Data
@Document(collection = "chefs")
public class Chef {
    @Id
    private String chefId;

    private String chefName;

    private String img;

    @JsonIgnore
    private String cloud_id;

    private boolean delFlg;

    @CreatedDate
    private DateTime createDate;

    @LastModifiedDate
    private DateTime updateDate;

    @LastModifiedBy
    private String updateUser;
}
