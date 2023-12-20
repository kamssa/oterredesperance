package com.operantic.projetcommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class UpdateProjetImageCommnad  {


    @TargetAggregateIdentifier
    String projetId;
    String cover;
}
