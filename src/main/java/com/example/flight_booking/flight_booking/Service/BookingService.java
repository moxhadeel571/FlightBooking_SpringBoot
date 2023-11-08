package com.example.flight_booking.flight_booking.Service;

import com.example.flight_booking.flight_booking.DAOModel.Flight;
import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import org.bson.types.ObjectId;

import java.util.List;

public interface BookingService {
    void saveBooking(Passenger flightBooking);

    List<Flight> searchFlights(String from, String to, String arrival, String departure);

    Passenger createPassenger(ObjectId flightId, Passenger passenger);
}
