package com.operantic.coreapi.coreapi.utilisateur.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UtilisateurRemovedEvent {

    private String userId;


}
