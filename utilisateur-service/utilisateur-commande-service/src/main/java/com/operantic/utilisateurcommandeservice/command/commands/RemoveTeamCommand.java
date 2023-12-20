package com.operantic.utilisateurcommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveTeamCommand {

    @TargetAggregateIdentifier
    String teamId;
}
