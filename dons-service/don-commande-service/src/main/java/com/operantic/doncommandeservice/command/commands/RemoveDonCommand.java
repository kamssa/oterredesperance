package com.operantic.doncommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class RemoveDonCommand {

    @TargetAggregateIdentifier
    String donId;
}
