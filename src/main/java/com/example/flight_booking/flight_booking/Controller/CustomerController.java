    package com.example.flight_booking.flight_booking.Controller;

    import com.example.flight_booking.flight_booking.BookingRepository.FlightRepository;
    import com.example.flight_booking.flight_booking.BookingRepository.PassengerRepository;
    import com.example.flight_booking.flight_booking.ServiceImplementation.PdfGenerator;
    import com.example.flight_booking.flight_booking.DAOModel.Coupon;
    import com.example.flight_booking.flight_booking.DAOModel.Email;
    import com.example.flight_booking.flight_booking.DAOModel.Flight;
    import com.example.flight_booking.flight_booking.DAOModel.Passenger;
    import com.example.flight_booking.flight_booking.Service.BookingService;
    import com.example.flight_booking.flight_booking.Service.CouponService;
    import com.example.flight_booking.flight_booking.Service.EmailService;
    import com.example.flight_booking.flight_booking.Service.FlightService;
    import jakarta.mail.MessagingException;
    import org.bson.types.ObjectId;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.io.ByteArrayInputStream;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    @Controller
    public class CustomerController {
        private BookingService bookingService;
        private FlightRepository flightRepository;
        private FlightService flightDAO;
        private PassengerRepository passengerRepository;
        @Autowired
        private CouponService couponService;
        private EmailService emailService;
         PdfGenerator pdfGenerator;
        @Autowired

        public CustomerController(BookingService bookingService, FlightRepository flightRepository, FlightService flightDAO, PassengerRepository passengerRepository, EmailService emailService, PdfGenerator pdfGenerator) {
            this.bookingService = bookingService;
            this.flightRepository = flightRepository;
            this.flightDAO = flightDAO;
            this.passengerRepository = passengerRepository;
            this.emailService = emailService;
            this.pdfGenerator = pdfGenerator;
        }


        @GetMapping("/customers/flights")
        public String ViewFligths(Model model){

            return "BookingPage";
        } @GetMapping("/customers/purchase/{id}")
        public String purchaseFlight(Model model,@PathVariable("id") ObjectId accessKey){
            Flight flightId=  flightRepository.findById(accessKey).orElse(null);;
            model.addAttribute("id",accessKey);
            model.addAttribute("flight",flightId);
            List<String> allSeats = generateSeatOptions();
            model.addAttribute("allSeats", allSeats);

            return "PurchaseFlight";
        }
        @PostMapping("/customer/redeem-promo/{id}")
        public String redeemPromoCode(
                @ModelAttribute("checkout") Passenger checkout,
                @ModelAttribute("user") Flight user,
                Model model,
                @PathVariable("id") ObjectId id,
                @RequestParam(name = "couponCode", required = false) String couponCode) {
            Flight flightinfo=flightRepository.findById(id).orElse(null); ;
            model.addAttribute("flight",flightinfo);
            Passenger passenger=passengerRepository.findById(String.valueOf(id)).orElse(null); ;
            model.addAttribute("passengers", passenger);
            // Get the cart items
            List<Passenger> cartItems = passengerRepository.findAll();
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("id", id);
            List<Flight> amounts =flightRepository.findAll();
            List<String> allSeats = generateSeatOptions();
            model.addAttribute("allSeats", allSeats);
            // Calculate the total cart amount before applying the discount
            Double totalAmount = amounts.get(0).getPrice();
            model.addAttribute("total", totalAmount);

            Coupon coupon = couponService.getCouponByCouponCode(couponCode);

            if (coupon != null) {
                double discountAmount = (totalAmount * coupon.getDiscountPercentage()) / 100.0;
                // ...

                // Calculate the new total sum after applying the discount to the total amount
                double newTotalSum = totalAmount - discountAmount;

                // Calculate and update the discounted price for each item
                for (Passenger item : cartItems) {
                    Double itemPrice = item.getFlight().getPrice();
                    Double itemDiscount = (itemPrice * discountAmount) / totalAmount;
                    Double newItemPrice = itemPrice - itemDiscount;

                    // Update the item price in the cart
                    item.getFlight().setPrice( newItemPrice);
                }

                // Pass the list of cart items, new total sum, and coupon details to the view
                model.addAttribute("newTotalSum", newTotalSum);
                model.addAttribute("couponCode", coupon.getCouponCode());
                model.addAttribute("discountPercentage", coupon.getDiscountPercentage());
            } else {
                // Handle the case where the coupon is not found (e.g., display an error message)
                model.addAttribute("couponNotFound", true);
            }

            List<String> indianStates = Arrays.asList(
                    "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                    "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                    "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
                    "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                    "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                    "Uttar Pradesh", "Uttarakhand", "West Bengal"
            );
            model.addAttribute("state", indianStates);
            // Redirect to the checkout page with the updated total sum
            return "PurchaseFlight";
        }
        private List<String> generateSeatOptions() {
            List<String> seatOptions = new ArrayList<>();
            char[] rows = {'A', 'B', 'C', 'D', 'E', 'F'};
            int numberOfSeatsInRow = 10;

            for (char row : rows) {
                for (int i = 1; i <= numberOfSeatsInRow; i++) {
                    seatOptions.add(row + String.valueOf(i));
                }
            }

            return seatOptions;
        }

        @PostMapping("/customers/booking/{id}")
        public String saveBooking(@PathVariable("id") ObjectId id, Passenger passenger, Email email, Flight flight) throws MessagingException {
            try {
                // Generate PDF
                byte[] pdfData = emailService.generateFlightTicketPdf(id);

                // Send booking confirmation email with PDF attachment
                emailService.sendBookingConfirmationEmail(email, id, pdfData);

                // Create passenger in the database
                bookingService.createPassenger(id, passenger);

                return "redirect:/customers/flights";
            } catch (Exception e) {
                // Handle exceptions, log errors, and return an appropriate error response
                return "error"; // You can customize the error handling logic here
            }
        }

        @PostMapping("/customers/ticket/{id}")
        public ResponseEntity<byte[]> downloadTicket(@PathVariable ObjectId id) {
            try {
                // Generate PDF
                byte[] pdfData = emailService.generateFlightTicketPdf(id);

                // Set response headers for PDF download
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "inline;filename=ticket.pdf");

                // Return the PDF data as ResponseEntity
                return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(pdfData);
            } catch (Exception e) {
                // Handle exceptions, log errors, and return an appropriate error response
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            }

        @GetMapping("/customer/search")
        public String searchFlights(
                                    @RequestParam("airport_from") String airport_from,
                                   @RequestParam("airport_to") String airport_to,
                                    Model model) {
            List<Flight> flights = flightRepository.findFlightsByParameters(airport_from, airport_to);
            model.addAttribute("flights", flights);
            return "searchResult";
        }



    }
