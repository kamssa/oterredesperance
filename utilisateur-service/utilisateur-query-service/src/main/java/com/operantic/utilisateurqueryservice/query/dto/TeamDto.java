package com.operantic.utilisateurqueryservice.query.dto;

import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private String id;
    private String nom;
    private String function;
    private String description;
    private String username;
    private String userCover;
    private String phone;
    private LocalDate localDate;
    private TeamStatus status;
}
