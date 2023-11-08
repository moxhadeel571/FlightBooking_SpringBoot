package com.example.flight_booking.flight_booking.BookingRepository;

import com.example.flight_booking.flight_booking.DAOModel.Flight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends MongoRepository<Flight, ObjectId> {
    Optional<Object> findById(String accessKey);



    @Query("{ 'airport_from': ?0, 'airport_to': ?1 }")
    List<Flight> findFlightsByParameters(String airport_from, String airport_to);
    // Custom query methods can be added here if needed
}
