package com.operantic.contactcommandeservice.command.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@ToString
@AllArgsConstructor
public class RemoveContactCommand {

    @TargetAggregateIdentifier
    String contactId;

}
