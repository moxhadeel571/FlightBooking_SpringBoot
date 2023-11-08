package com.example.flight_booking.flight_booking.ServiceImplementation;

import com.example.flight_booking.flight_booking.BookingRepository.PassengerRepository;
import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Configuration
public class PdfGenerator {
@Autowired
    private static PassengerRepository passengerRepository ;



    public static byte[] generateFlightTicketPdf(ObjectId id) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        try (Document document = new Document(pdfDocument)) {
            // Apply Bootstrap classes to elements for styling
            Paragraph header = new Paragraph("Flight Ticket")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(24) // Set font size directly
                    .setMarginBottom(20); // Set margin directly
            document.add(header);

            Passenger passengerDetails = passengerRepository.findByIdObject(id).orElse(null);

            if (passengerDetails != null) {
                Div flightDetails = new Div()

                        .add(new Paragraph("Flight Number: " + passengerDetails.getFlight().getFlightNumber()))
                        .add(new Paragraph("From: " + passengerDetails.getFlight().getAirport_from()))
                        .add(new Paragraph("To: " + passengerDetails.getFlight().getAirport_to()))
                        .add(new Paragraph("Seat Number: " + passengerDetails.getSeats()))
                        .setMarginBottom(20); // Set margin directly
                document.add(flightDetails);

                Div passengerDetailsDiv = new Div()
                        .add(new Paragraph("Passenger Name: " + passengerDetails.getPersonalName()))
                        .add(new Paragraph("Mobile Number: " + passengerDetails.getPersonalMobile()))
                        .setMarginBottom(20); // Set margin directly
                document.add(passengerDetailsDiv);

                // Ticket footer with Bootstrap alert class
                Paragraph footer = new Paragraph("Thank you for choosing our airline!")
                        .setTextAlignment(TextAlignment.CENTER);
                DeviceRgb backgroundColor = new DeviceRgb(248, 215, 218); // RGB values for the background color
                footer.setBackgroundColor(backgroundColor);
                footer.setFontColor(new DeviceRgb(114, 28, 36)); // RGB values for the text color
                footer.setPadding(10);
                document.add(footer);
            }
        }
        return outputStream.toByteArray();
    }
}
