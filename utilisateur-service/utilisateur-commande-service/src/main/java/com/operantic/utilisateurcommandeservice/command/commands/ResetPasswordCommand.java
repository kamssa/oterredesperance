package com.operantic.utilisateurcommandeservice.command.commands;

import lombok.Getter;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Getter
@ToString
public class ResetPasswordCommand {

    @TargetAggregateIdentifier
    String utilisateurId;
    String email;
    String resetPassword;

    public ResetPasswordCommand(String utilisateurId, String email, String resetPassword) {
        this.utilisateurId = utilisateurId;
        this.email = email;
        this.resetPassword=resetPassword;
    }
}
