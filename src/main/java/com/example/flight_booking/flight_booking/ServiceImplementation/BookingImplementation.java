package com.example.flight_booking.flight_booking.ServiceImplementation;

import com.example.flight_booking.flight_booking.BookingRepository.FlightRepository;
import com.example.flight_booking.flight_booking.BookingRepository.PassengerRepository;
import com.example.flight_booking.flight_booking.DAOModel.Flight;
import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import com.example.flight_booking.flight_booking.Exception.FlightNotFoundException;
import com.example.flight_booking.flight_booking.Service.BookingService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingImplementation implements BookingService {
    private MongoTemplate mongoTemplate;

    private final PassengerRepository bookingRepo;
    private FlightRepository flightRepository;
    private PassengerRepository passengerRepository;
    @Autowired
    public BookingImplementation(MongoTemplate mongoTemplate,  PassengerRepository bookingRepo, FlightRepository flightRepository, PassengerRepository passengerRepository) {
        this.mongoTemplate = mongoTemplate;
        this.bookingRepo = bookingRepo;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public void saveBooking(Passenger flightBooking) {
        bookingRepo.save(flightBooking);
    }

    @Override
    public List<Flight> searchFlights(String from, String to, String arrival, String departure) {
        return null;
    }

    @Override
    public Passenger createPassenger(ObjectId flightId, Passenger passenger) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);

        if (optionalFlight.isPresent()) {
            Flight selectedFlight = optionalFlight.get();
            List<Passenger> selectedPassenger = new ArrayList<>();
            Passenger passengerlist=new Passenger();
            passengerlist.setId(passenger.getId());
            passengerlist.setSeats(passenger.getSeats());
            passengerlist.setBusiness(passenger.getBusiness());
            passengerlist.setPincode(passenger.getPincode());
            passengerlist.setChildren(passenger.getChildren());
            passengerlist.setSaveAddress(passenger.getSaveAddress());
            passengerlist.setBillingAddress(passenger.getBillingAddress());
            passengerlist.setState(passenger.getState());
            passengerlist.setAdults(passenger.getAdults());
            passengerlist.setSeats(passenger.getSeats());
            passengerlist.setPersonalEmail(passenger.getPersonalEmail());
            passengerlist.setPersonalName(passenger.getPersonalName());
            passengerlist.setPersonalMobile(passenger.getPersonalMobile());
            selectedPassenger.add(passengerlist);
            // Associate the passenger with the selected flight
            passenger.setFlight(selectedFlight);
 // Save the passenger
            return passengerRepository.save(passenger);
        } else {
            // Handle the case where the flight with the given id does not exist
            throw new FlightNotFoundException("Flight not found with id: " + flightId);
        }
    }
    }





