package com.operantic.coreapi.coreapi.don.event;

import com.operantic.coreapi.coreapi.don.enumeted.DonStatus;
import com.operantic.coreapi.event.BaseEvent;
import lombok.Getter;

public class DonActivatedEvent extends BaseEvent<String> {

    @Getter
    DonStatus donStatus;

    public DonActivatedEvent(String id, DonStatus donStatus){
        super(id);
        this.donStatus = donStatus;
    }
}
