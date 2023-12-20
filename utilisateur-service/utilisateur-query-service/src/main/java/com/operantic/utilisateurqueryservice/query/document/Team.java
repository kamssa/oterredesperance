package com.operantic.utilisateurqueryservice.query.document;

import com.operantic.coreapi.coreapi.team.enumereted.TeamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @MongoId
    private String id;
    private String nom;
    private String function;
    private String description;
    private String username;
    private String userCover;
    private String phone;

    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private TeamStatus status;

    public Team(String id, String nom, String function, String description, String username, String phone, TeamStatus status) {
        this.id = id;
        this.nom = nom;
        this.function = function;
        this.description = description;
        this.username = username;
        this.phone = phone;
        this.status = status;
    }
}
