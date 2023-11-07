package com.example.ecommerce.ecommerce.Service;

import com.example.ecommerce.ecommerce.Entity.Email;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


@Component

public interface EmailService {



    void sendEmail(Email email) throws MessagingException, MessagingException;


    void order_email(Email email, ObjectId id) throws MessagingException;
}
