package com.operantic.doncommandeservice.command.dto;

import com.operantic.coreapi.ProjetData;
import com.operantic.coreapi.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDonRequestDTO {

    String id;
    UserData userData;
    ProjetData projetData;
}
