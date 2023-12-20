package com.operantic.coreapi.coreapi.utilisateur.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class PasswordResetedEvent {

    @Getter
    private String userId;

    @Getter
    private String email;

    @Getter
    private String resetPassword;

}
