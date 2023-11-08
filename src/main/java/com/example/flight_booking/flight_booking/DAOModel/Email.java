package com.example.flight_booking.flight_booking.DAOModel;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "mails")
public class Email {
    @Id
    private String from;
    private String to;
    private String subject;
    private String body;
}
