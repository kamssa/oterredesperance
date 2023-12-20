package com.operantic.utilisateurcommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class UpdateProfileImageCommnad {
    @TargetAggregateIdentifier
    String teamId;
    String profilImage;
}
