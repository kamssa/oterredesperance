package com.operantic.contactqueryservice.query.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @MongoId
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String message;
    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private ContactStatus status;


}
