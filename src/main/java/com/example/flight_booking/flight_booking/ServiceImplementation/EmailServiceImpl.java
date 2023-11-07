package com.example.ecommerce.ecommerce.Implementation;


import com.example.ecommerce.ecommerce.Entity.Email;
import com.example.ecommerce.ecommerce.Entity.Products;
import com.example.ecommerce.ecommerce.Entity.checkOut;
import com.example.ecommerce.ecommerce.Repository.checkOutRepository;
import com.example.ecommerce.ecommerce.Service.EmailService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
@Autowired
private checkOutRepository checkOutRepository;

        @Autowired
        public EmailServiceImpl(JavaMailSender javaMailSender) {
            this.javaMailSender = javaMailSender;
        }

        @Override
        public void sendEmail(Email email) throws MessagingException {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            try {
                String getSubject = "🍔🥗 Savor the Latest: Exclusive Food Delivery App Updates Await You!";
                String getBody = "Discover Delights, Delivered to You!\n" +
                        "\n" +
                        "Hungry for more? Stay ahead of the flavor curve with our food delivery app updates. From sizzling deals to new menu must-haves, be the first to know about all things delicious. Join now and let your cravings lead the way!";
                String displayName = "Savor Delights"; // Set the desired display name
                String fromEmail = "noreply@example.com"; // Set your actual email address

                helper.setFrom(new InternetAddress(fromEmail, displayName));
                helper.setTo(email.getTo());
                helper.setSubject(getSubject);
                helper.setText(getBody, true);

                javaMailSender.send(mimeMessage);

            } catch (Exception e) {
                // Handle other exceptions
                // For example, you may log the error or display a custom error message
                e.printStackTrace();
            }
        }

    @Override
    public void order_email(Email email, ObjectId id) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        try {
            // Obtain the necessary data for email and ID
            String toEmail = email.getTo();  // Make sure your Email object contains a valid 'to' address
            // ObjectId id = ...;  // Obtain the order ID, e.g., from a checkOut object
            checkOut food = checkOutRepository.findById(id).orElse(null);
            ObjectId Orderid = food.getId();
            Double Total = food.getTotalAmount();
            String Food = String.valueOf(food.getProductNames());

            String getSubject = "Order Confirmation - Your Order Id " + Orderid;
            String displayName = "Anonymous"; // Set the desired display name
            String fromEmail = "noreply@example.com"; // Set your actual email address

            helper.setFrom(new InternetAddress(fromEmail, displayName));
            helper.setTo(toEmail);
            helper.setSubject(getSubject);

            // Create the HTML content for the email
            String htmlContent = "<html><body>"
                    + "<h1>Order Confirmation</h1>"
                    + "<p>Thank you for placing your order with us. Your order for " + Food + " will be on the way soon!</p>"
                    + "<p>Total Amount: $" + Total + "</p>"
                    + "<p>Order ID: " + Orderid + "</p>"
                    + "<p>Order Details:</p>"
                    + "<ul>";

            // Loop through the product names and add them to the email
            for (String productName : food.getProductNames()) {
                htmlContent += "<li>" + productName + "</li>";
            }

            htmlContent += "</ul>"
                    + "<p>For order tracking details, please visit our <a href=\"https://yourwebsite.com/order-tracking\">Order Tracking</a> page.</p>"
                    + "<img src=\"https://yourwebsite.com/images/ecommerce-image.jpg\" alt=\"E-commerce Image\">"
                    + "<p>Estimated Delivery Date: " + food.getOrderdate() + "</p>"
                    + "<p>Delivery Address: " + food.getUserInfo().getAddressLine1() +food.getUserInfo().getAddressLine2() +"</p>"
                    + "</body></html>";

            // Set the HTML content of the email
            helper.setText(htmlContent, true);

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

    // A utility method to format the date as a string
  }







