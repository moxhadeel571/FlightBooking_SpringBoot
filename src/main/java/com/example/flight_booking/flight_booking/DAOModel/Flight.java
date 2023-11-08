package com.example.flight_booking.flight_booking.DAOModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Getter
@Setter
@Document(collection = "flights")
public class Flight {
    @MongoId
    private ObjectId id;
    private String airport_from ;
    private String airport_to ;
    private String airlineName ;
    private String flightNumber;
    private String currencyCode;
    private String departureAirportCode;
    private String arrivalAirportCode;
    private String Terminal;
    private String travelClass;
    @Field("departureDateTime")
    private LocalDateTime departureDateTime;
    @Field("arrivalDateTime")
    private LocalDateTime arrivalDateTime;
    private int availableSeats;
    private Double Price;
    private int taxesAndFees;
    @Field(name="Adults")
    private int adults;
    private int seniors;
    private Boolean Available;
    private Integer Baggage;
    private String checkIn;
        private String Seat;
    private Integer cabIn;
    // Other flight attributes, getters, and setter
}
