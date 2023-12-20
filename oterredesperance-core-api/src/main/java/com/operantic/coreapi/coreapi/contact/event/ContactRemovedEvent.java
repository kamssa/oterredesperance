package com.operantic.coreapi.coreapi.contact.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
@AllArgsConstructor
public class ContactRemovedEvent {

    private String contactId;

}
