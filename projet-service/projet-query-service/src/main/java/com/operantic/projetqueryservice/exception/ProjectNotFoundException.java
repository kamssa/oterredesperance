package com.operantic.projetqueryservice.exception;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(){
        super("Projet Not Found");
    }
}
