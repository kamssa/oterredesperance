package com.operantic.contactcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactRequestDTO {

	String id;

	String nom;

	String email;

	String telephone;

	String adresse;

	String message;

}
