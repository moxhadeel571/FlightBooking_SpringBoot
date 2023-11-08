package com.example.flight_booking.flight_booking.Service;

import com.example.flight_booking.flight_booking.BookingRepository.FlightRepository;
import com.example.flight_booking.flight_booking.DAOModel.Flight;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

public interface FlightService {
    void createFlight(Flight flight);
}
