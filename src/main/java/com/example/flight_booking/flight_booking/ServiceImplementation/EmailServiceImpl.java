package com.example.flight_booking.flight_booking.ServiceImplementation;



import com.example.flight_booking.flight_booking.BookingRepository.PassengerRepository;
import com.example.flight_booking.flight_booking.DAOModel.Email;
import com.example.flight_booking.flight_booking.DAOModel.Passenger;
import com.example.flight_booking.flight_booking.Service.EmailService;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import jakarta.mail.MessagingException;
import jakarta.activation.DataSource;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class EmailServiceImpl implements EmailService {
@Autowired
  private PassengerRepository passengerRepository;
  private final JavaMailSender javaMailSender;
  @Autowired
  public EmailServiceImpl(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }


  @Override
  public void sendBookingConfirmationEmail(Email email, ObjectId id, byte[] pdfData) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

    try {
      // Obtain the necessary data for email and ID
      String toEmail = email.getTo();  // Make sure your Email object contains a valid 'to' address
      Passenger passengerDetails = passengerRepository.findByIdObject(id).orElse(null);

      if (passengerDetails == null) {
        // Handle the case when the passenger with the given id doesn't exist
        throw new IllegalArgumentException("Passenger not found with ID: " + id);
      }

      String subject = "Booking Confirmation - Passenger ID: " + id;
      String displayName = "Your Airline"; // Set the desired display name
      String fromEmail = "noreply@example.com"; // Set your actual email address

      helper.setFrom(new InternetAddress(fromEmail, displayName));
      helper.setTo(toEmail);
      helper.setSubject(subject);

      // Create the HTML content for the email with Bootstrap styling
      String htmlContent = "<html><head>"
              + "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css\">"
              + "</head><body>"
              + "<div class=\"container p-4 border rounded\">"
              + "<h1 class=\"mb-4\">Booking Confirmation</h1>"
              + "<div class=\"row mb-3\">"
              + "<div class=\"col-md-6\">"
              + "<strong>Passenger ID:</strong> " + id + "<br>"
              + "<strong>Name:</strong> " + passengerDetails.getPersonalName() + "<br>"
              + "<strong>Email:</strong> " + passengerDetails.getPersonalEmail() + "<br>"
              + "<strong>Mobile:</strong> " + passengerDetails.getPersonalMobile() + "<br>"
              + "</div>"
              + "<div class=\"col-md-6\">"
              + "<strong>Flight Number:</strong> " + passengerDetails.getFlight().getFlightNumber() + "<br>"
              + "<strong>Airline:</strong> " + passengerDetails.getFlight().getAirlineName() + "<br>"
              + "<strong>Departure Airport:</strong> " + passengerDetails.getFlight().getDepartureAirportCode() + "<br>"
              + "<strong>Arrival Airport:</strong> " + passengerDetails.getFlight().getArrivalAirportCode() + "<br>"
              // Add more flight details as needed
              + "</div>"
              + "</div>"
              + "</div>"
              + "</body></html>";

      helper.setText(htmlContent, true);

      // Attach the PDF to the email using ByteArrayDataSource
      DataSource dataSource = new ByteArrayDataSource(pdfData, "application/pdf");
      helper.addAttachment("FlightDetails.pdf", (jakarta.activation.DataSource) dataSource);

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      // Handle messaging exception
      throw e;
    } catch (Exception e) {
      // Handle other exceptions
      // For example, you may log the error or display a custom error message
      e.printStackTrace();
    }
  }
  @Override
  public byte[] generateFlightTicketPdf(ObjectId id) {
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
                .add(new Paragraph("Seat Number: " + passengerDetails.getFlight().getSeat()))
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

  // A utility method to format the date as a string
  }







