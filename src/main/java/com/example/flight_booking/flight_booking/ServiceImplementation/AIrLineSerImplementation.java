package com.example.flight_booking.flight_booking.ServiceImplementation;

import com.example.flight_booking.flight_booking.BookingRepository.FlightRepository;
import com.example.flight_booking.flight_booking.DAOModel.Flight;
import com.example.flight_booking.flight_booking.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AIrLineSerImplementation implements FlightService{
    private FlightRepository flightRepository;

    @Autowired
    public AIrLineSerImplementation(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public void createFlight(Flight flight) {
        flightRepository.save(flight);

    }
}
