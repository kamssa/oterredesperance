package com.operantic.utilisateurcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUtilisateurDTO {

	String nom;
	String prenom;
	String username;
	String email;
	String telephone;
	String adresse;
	String password;
	List<String> roles;



}
