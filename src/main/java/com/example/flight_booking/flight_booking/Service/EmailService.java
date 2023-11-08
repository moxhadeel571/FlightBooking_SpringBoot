package com.example.flight_booking.flight_booking.Service;


import com.example.flight_booking.flight_booking.DAOModel.Email;

import jakarta.mail.MessagingException;
import org.bson.types.ObjectId;


public interface EmailService {


    void sendBookingConfirmationEmail(Email email, ObjectId id, byte[] pdfData) throws MessagingException;

    byte[] generateFlightTicketPdf(ObjectId id);
}
