package com.operantic.utilisateurcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUtilisateurDTO {

	String nom;
	String prenom;
	String email;
	String telephone;
	String adresse;
	String profilImage;
	String password;
	List<String> roles;
	String username;

}
