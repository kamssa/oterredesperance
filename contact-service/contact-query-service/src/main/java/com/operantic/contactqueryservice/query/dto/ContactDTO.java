package com.operantic.contactqueryservice.query.dto;

import com.operantic.coreapi.coreapi.contact.enumeted.ContactStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
    private String message;
    private ContactStatus status;
    
}
