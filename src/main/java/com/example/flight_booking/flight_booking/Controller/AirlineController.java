package com.example.flight_booking.flight_booking.Controller;

import com.example.flight_booking.flight_booking.BookingRepository.FlightRepository;
import com.example.flight_booking.flight_booking.BookingRepository.PassengerRepository;
import com.example.flight_booking.flight_booking.DAOModel.Coupon;
import com.example.flight_booking.flight_booking.DAOModel.Flight;
import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import com.example.flight_booking.flight_booking.Service.BookingService;
import com.example.flight_booking.flight_booking.Service.CouponService;
import com.example.flight_booking.flight_booking.Service.FlightService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;



@Controller
public class AirlineController {

private FlightRepository flightDAO;
private PassengerRepository passengerRepository;
private FlightService flightService;
private CouponService couponService;
private BookingService bookingService;
@Autowired
    public AirlineController(FlightRepository flightDAO, PassengerRepository passengerRepository, FlightService flightService, CouponService couponService, BookingService bookingService) {
        this.flightDAO = flightDAO;
    this.passengerRepository = passengerRepository;
    this.flightService = flightService;
    this.couponService = couponService;
    this.bookingService = bookingService;
}

    @GetMapping("/airline/dashboard")
public String DashboardController(Model model,@ModelAttribute(name="coupon")Coupon coupon
                                ) {
        List<Flight> flightinfo=flightDAO.findAll() ;
        model.addAttribute("flight",flightinfo);
        List<Passenger> passenger=passengerRepository.findAll() ;
        model.addAttribute("passengers", passenger);
return "dashboard";
}


    @GetMapping("/seller/coupon-form")
    public String showCouponForm(Model model) {
        model.addAttribute("coupon", new Coupon());
        return "redirect:/airline/dashboard"; // Return the name of the HTML template for the coupon form
    }
    @PostMapping("/seller/generate-coupon")
    public String generateCoupon(@ModelAttribute("coupon") Coupon coupon) {
        couponService.generateCoupon(coupon.getCouponCode(), (coupon.getDiscountPercentage()));
        return "redirect:/airline/dashboard";// Redirect to a list of coupons or another page
    }
@PostMapping("/airline/save")
public String SaveController(Flight flight) {
    flightService.createFlight(flight);
    return "redirect:/airline/dashboard";
}



}
