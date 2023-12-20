package com.operantic.coreapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserData {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String username;
    private String telephone;
    private String resetPassword;
}
