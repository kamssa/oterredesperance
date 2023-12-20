package com.operantic.coreapi.coreapi.don.event;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DonUpdatedEvent {

    String donId;
    UserData userData;
    ProjetData projetData;
}
