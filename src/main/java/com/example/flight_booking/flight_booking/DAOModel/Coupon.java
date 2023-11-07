package com.example.ecommerce.ecommerce.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
@Document(collection = "PromoCode")
public class Coupon  {
    private ObjectId id;
    private String couponCode;
    private Double  discountPercentage;
    private Double  discountAmount;
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

}
