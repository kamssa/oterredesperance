package com.operantic.doncommandeservice.command.commands;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import com.operantic.coreapi.commands.BaseCommand;
import lombok.Getter;

public class CreateDonCommand extends BaseCommand<String> {

    @Getter
    UserData userdata;
    @Getter
    ProjetData projetData;

    public CreateDonCommand(String id){
        super(id);
    }

    public CreateDonCommand(String id, UserData userdata, ProjetData projetData) {
        super(id);
        this.userdata = userdata;
        this.projetData = projetData;
    }


}
