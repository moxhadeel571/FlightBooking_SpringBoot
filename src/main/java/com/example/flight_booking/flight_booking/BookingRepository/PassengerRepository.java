package com.example.flight_booking.flight_booking.BookingRepository;

import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends MongoRepository<Passenger, String> {
    @Query("{ '_id' : ?0 }")
    Optional<Passenger> findByIdObject(ObjectId id);

    // Custom query methods can be added here if needed
}
