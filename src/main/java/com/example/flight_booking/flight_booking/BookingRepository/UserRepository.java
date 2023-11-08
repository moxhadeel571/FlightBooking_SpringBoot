package com.example.flight_booking.flight_booking.BookingRepository;


import com.example.flight_booking.flight_booking.DAOModel.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    User findFirstByEmail(String email);


}