package com.operantic.doncommandeservice.command.commands;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class UpdatedDonCommand {

    @TargetAggregateIdentifier
    String donId;
    UserData userData;
    ProjetData projetData;
}
