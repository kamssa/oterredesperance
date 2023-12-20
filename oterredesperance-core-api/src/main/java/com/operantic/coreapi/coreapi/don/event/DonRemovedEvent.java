package com.operantic.coreapi.coreapi.don.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class DonRemovedEvent {

    private String donId;
}
