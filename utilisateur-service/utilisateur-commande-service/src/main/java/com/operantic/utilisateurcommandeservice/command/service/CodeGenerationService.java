package com.operantic.utilisateurcommandeservice.command.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class CodeGenerationService {

    private static final String CODE_CHARACTERS = "0123456789";
    private static final int CODE_LENGTH = 6;

    public String generateCode() {
        Random random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CODE_CHARACTERS.length());
            char randomChar = CODE_CHARACTERS.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
