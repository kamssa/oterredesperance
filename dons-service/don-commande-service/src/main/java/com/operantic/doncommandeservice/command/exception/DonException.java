package com.operantic.doncommandeservice.command.exception;

public class DonException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public DonException(String message){
        super(message);
    }
}
