package com.example.flight_booking.flight_booking.DAOModel;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Getter
@Setter
@Document(collection = "passengers")
public class Passenger {
    @MongoId
    private ObjectId id;
    private String adults;
    private String children;
    private String pincode;
    private String state;
    private String billingAddress;
    private String saveAddress;
    private String personalName;
    private String personalEmail;
    private String personalMobile;
    private String business;
    private String seats; // Change the type to List<Seat>
    @DBRef
    private Flight flight;

    // Other passenger attributes, getters, and setters
}
