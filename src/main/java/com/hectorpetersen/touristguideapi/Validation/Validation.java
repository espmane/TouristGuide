package com.hectorpetersen.touristguideapi.Validation;

import org.springframework.stereotype.Component;

@Component
public class Validation {

    public void validateName(String name) {

        if (name == null){
            throw new IllegalArgumentException("Name can not be empty.");
        }

        if (!name.matches("[a-zA-ZæøåÆØÅ ]+")){
            throw new IllegalArgumentException("Name contains invalid characters");
        }
    }
}