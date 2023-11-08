package com.example.flight_booking.flight_booking.Service;

import com.example.flight_booking.flight_booking.DAOModel.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {



 User saveUser(User User);


 void removeSuccessMessage();
}