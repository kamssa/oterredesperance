package com.operantic.coreapi.coreapi.projet.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ImageUpdatedEvent {
    private String projetId;
    private String image;
}
