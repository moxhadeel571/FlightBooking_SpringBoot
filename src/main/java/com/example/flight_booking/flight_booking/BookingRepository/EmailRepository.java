package com.example.flight_booking.flight_booking.BookingRepository;

import com.example.flight_booking.flight_booking.DAOModel.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository< Email,ObjectId> {
}
