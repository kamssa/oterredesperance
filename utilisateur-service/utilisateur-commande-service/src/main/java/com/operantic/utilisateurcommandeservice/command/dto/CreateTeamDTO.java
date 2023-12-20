package com.operantic.utilisateurcommandeservice.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeamDTO {

    private String nom;
    private String function;
    private String description;
    private String username;
    private String userCover;
    private String phone;
    private LocalDate localDate;
}
