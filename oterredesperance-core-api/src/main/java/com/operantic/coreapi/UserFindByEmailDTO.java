package com.operantic.coreapi;

public class UserFindByEmailDTO {
    private String email;

    public String getEmail() {
        return email;
    }

    public UserFindByEmailDTO(String email) {
        this.email = email;
    }
}
